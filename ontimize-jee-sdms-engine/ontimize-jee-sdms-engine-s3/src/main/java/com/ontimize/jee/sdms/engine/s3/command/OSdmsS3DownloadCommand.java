package com.ontimize.jee.sdms.engine.s3.command;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.sdms.common.command.IOSdmsCommand;
import com.ontimize.jee.sdms.common.inyector.IOSdmsInyector;
import com.ontimize.jee.sdms.common.response.builder.IOSdmsResponseBuilder;
import com.ontimize.jee.sdms.common.workspace.manager.IOSdmsWorkspaceManager;
import com.ontimize.jee.sdms.common.zip.IOSdmsZipCompressor;
import com.ontimize.jee.sdms.common.zip.OSdmsZipDto;
import com.ontimize.jee.sdms.engine.s3.repository.IOSdmsS3Repository;
import com.ontimize.jee.sdms.engine.s3.repository.OSdmsS3RepositoryProxy;
import com.ontimize.jee.sdms.engine.s3.repository.dto.OSdmsS3RepositoryDto;
import com.ontimize.jee.sdms.engine.s3.repository.response.OSdmsS3RepositoryResponse;
import com.ontimize.jee.sdms.engine.s3.repository.response.codes.OSdmsS3RepositoryResponseCodes;
import com.ontimize.jee.sdms.engine.s3.util.config.IOSdmsS3EngineConfig;
import com.ontimize.jee.sdms.engine.s3.util.input.filter.OSdmsS3InputFilter;
import com.ontimize.jee.sdms.engine.s3.util.input.filter.reader.IOSdmsS3FilterReader;
import com.ontimize.jee.sdms.engine.s3.util.normalize.IOSdmsS3KeyNormalize;
import com.ontimize.jee.sdms.engine.s3.util.response.mapper.IOSdmsS3ResponseMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Command to download files from S3
 */
public class OSdmsS3DownloadCommand implements IOSdmsCommand {

    //Constants
    private static final String ZIP_NAME = "data.zip";


    //Messages
    private static final String MESSAGE_ERROR_NO_ACTIVE_WORKSPACE = "No active workspace found";
    private static final String MESSAGE_ERROR_NO_BUCKET = "No S3 bucket has been configured";
    private static final String MESSAGE_NO_CONTENT = "No file has been obtained from the query";


    // Dependencies
    private IOSdmsS3Repository repository;
    private IOSdmsResponseBuilder responseBuilder;
    private IOSdmsS3ResponseMapper responseMapper;
    private IOSdmsZipCompressor zipCompressor;
    private IOSdmsWorkspaceManager workspaceManager;


    //Data
    private String bucket;
    private OSdmsS3InputFilter filter;
    private List<String> queries = new ArrayList<>();


    //Respone
    private OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> response;

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| ENTRYPOINT |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3DownloadCommand( final OSdmsS3InputFilter filter ) {
        this.filter = filter;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| INIT  |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void init( final IOSdmsInyector inyector ) {
        //Inyect dependencies
        this.repository = inyector.get( OSdmsS3RepositoryProxy.class );
        this.responseBuilder = inyector.get( IOSdmsResponseBuilder.class );
        this.responseMapper = inyector.get( IOSdmsS3ResponseMapper.class );
        this.zipCompressor = inyector.get( IOSdmsZipCompressor.class );
        this.workspaceManager = inyector.get( IOSdmsWorkspaceManager.class );
        final IOSdmsS3FilterReader filterParamReader = inyector.get( IOSdmsS3FilterReader.class );
        final IOSdmsS3EngineConfig s3EngineConfig = inyector.get( IOSdmsS3EngineConfig.class );
        final IOSdmsS3KeyNormalize keyNormalize = inyector.get( IOSdmsS3KeyNormalize.class );

        //Get Data
        this.workspaceManager.active( this.filter.getWorkspace(), this.filter.getData() );
        this.bucket = s3EngineConfig.getBucket();
        this.queries = filterParamReader.readAllKeys( this.filter );
        this.queries = this.queries.stream().map( keyNormalize::normalize ).collect( Collectors.toList() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| VALIDATE |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult validate() {
        if( this.workspaceManager.getActive() == null ) {
            return this.responseBuilder
                    .code( EntityResult.OPERATION_WRONG )
                    .message( MESSAGE_ERROR_NO_ACTIVE_WORKSPACE )
                    .build();
        }

        if( this.bucket == null ) {
            return this.responseBuilder
                    .code( EntityResult.OPERATION_WRONG )
                    .message( MESSAGE_ERROR_NO_BUCKET )
                    .build();
        }

        return null;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| RUN |----------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void run() {
        final List<ListObjectsRequest> requests = new ArrayList<>();

        this.queries.forEach( prefix -> {
            final ListObjectsRequest request = new ListObjectsRequest()
                    .withBucketName( this.bucket )
                    .withPrefix( prefix );

            if( this.filter.hasMaxKeys() ) request.withMaxKeys( this.filter.getMaxKeys() );
            if( this.filter.hasDelimiter() ) request.withDelimiter( this.filter.getDelimiter() );
            if( this.filter.hasMarker() ) request.withMarker( this.filter.getMarker() );

            requests.add( request );
        } );

        this.response = this.repository.download( requests );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| RESPONSE |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult response() {
        EntityResult result = null;

        if( this.response != null ) {
            final OSdmsS3RepositoryResponseCodes code = this.response.getCode();
            if( code == OSdmsS3RepositoryResponseCodes.OK ) {
                result = this.responseBuilder
                        .code( EntityResult.OPERATION_SUCCESSFUL )
                        .message( MESSAGE_NO_CONTENT )
                        .build();

                List<OSdmsS3RepositoryDto> data = this.response.getData().stream()
                        .filter( target -> ! target.getName().equals( OSdmsS3RepositoryDto.FILE_NAME_MARK_FOLDER ) )
                        .collect( Collectors.toList() );

                if( ! data.isEmpty() ) {
                    final OSdmsZipDto zip = this.zipCompressor.compress( ZIP_NAME, data );
                    result = this.responseMapper.map( zip );
                }
            }
        }

        if( result == null ) result = this.responseMapper.map( this.response );

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
