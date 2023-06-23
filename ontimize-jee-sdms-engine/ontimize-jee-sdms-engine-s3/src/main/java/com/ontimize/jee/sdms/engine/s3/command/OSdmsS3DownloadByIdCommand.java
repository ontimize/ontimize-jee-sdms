package com.ontimize.jee.sdms.engine.s3.command;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.sdms.common.command.IOSdmsCommand;
import com.ontimize.jee.sdms.common.crypter.IOSdmsCrypter;
import com.ontimize.jee.sdms.common.crypter.OSdmsBase64Crypter;
import com.ontimize.jee.sdms.common.inyector.IOSdmsInyector;
import com.ontimize.jee.sdms.common.path.validator.IOSdmsPathValidator;
import com.ontimize.jee.sdms.common.response.builder.IOSdmsResponseBuilder;
import com.ontimize.jee.sdms.common.workspace.OSdmsWorkspace;
import com.ontimize.jee.sdms.common.workspace.manager.IOSdmsWorkspaceManager;
import com.ontimize.jee.sdms.engine.s3.repository.IOSdmsS3Repository;
import com.ontimize.jee.sdms.engine.s3.repository.OSdmsS3RepositoryProxy;
import com.ontimize.jee.sdms.engine.s3.repository.dto.OSdmsS3RepositoryDto;
import com.ontimize.jee.sdms.engine.s3.repository.response.OSdmsS3RepositoryResponse;
import com.ontimize.jee.sdms.engine.s3.repository.response.codes.OSdmsS3RepositoryResponseCodes;
import com.ontimize.jee.sdms.engine.s3.util.config.IOSdmsS3EngineConfig;
import com.ontimize.jee.sdms.engine.s3.util.input.filter.OSdmsS3InputFilter;
import com.ontimize.jee.sdms.engine.s3.util.normalize.IOSdmsS3KeyNormalize;
import com.ontimize.jee.sdms.engine.s3.util.response.mapper.IOSdmsS3ResponseMapper;

import java.util.List;


/**
 * Command to download a file from S3 by its id
 */
public class OSdmsS3DownloadByIdCommand implements IOSdmsCommand {

    //Messages
    private static final String MESSAGE_ERROR_NO_ACTIVE_WORKSPACE = "No active workspace found";
    private static final String MESSAGE_ERROR_NO_KEY = "No valid S3 key found";
    private static final String MESSAGE_ERROR_INVALID_KEY_FOR_WORKSPACE = "The S3 key is invalid for the selected workspace";
    private static final String MESSAGE_ERROR_NOT_FOUND = "The object with the provided key was not found in S3";


    // Dependencies
    private IOSdmsS3Repository repository;
    private IOSdmsWorkspaceManager workspaceManager;
    private OSdmsWorkspace workspace;
    private IOSdmsResponseBuilder responseBuilder;
    private IOSdmsS3ResponseMapper responseMapper;
    private IOSdmsPathValidator pathValidator;


    //Data
    private String bucket;
    private OSdmsS3InputFilter filter;
    private String id;
    private String key;


    //Response
    private OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> response;

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| ENTRYPOINT |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3DownloadByIdCommand( final String id, final OSdmsS3InputFilter filter ) {
        this.id = id;
        this.filter = filter;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| INIT |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void init( final IOSdmsInyector inyector ) {
        //Inyect dependencies
        this.repository = inyector.get( OSdmsS3RepositoryProxy.class );
        this.workspaceManager = inyector.get( IOSdmsWorkspaceManager.class );
        this.responseBuilder = inyector.get( IOSdmsResponseBuilder.class );
        this.responseMapper = inyector.get( IOSdmsS3ResponseMapper.class );
        this.pathValidator = inyector.get( IOSdmsPathValidator.class );
        final IOSdmsCrypter crypter = inyector.get( OSdmsBase64Crypter.class );
        final IOSdmsS3EngineConfig s3EngineConfig = inyector.get( IOSdmsS3EngineConfig.class );
        final IOSdmsS3KeyNormalize keyNormalize = inyector.get( IOSdmsS3KeyNormalize.class );

        //Get Data
        this.workspaceManager.active( this.filter.getWorkspace(), this.filter.getData() );
        this.workspace = workspaceManager.getActive();
        this.bucket = s3EngineConfig.getBucket();
        this.key = crypter.decode( this.id );
        this.key = keyNormalize.normalize( this.key );
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

        if( this.key == null ) {
            return this.responseBuilder
                    .code( EntityResult.OPERATION_WRONG )
                    .message( MESSAGE_ERROR_NO_KEY )
                    .build();
        }

        if( ! this.pathValidator.validate( this.key, this.workspace.getPatterns() ) ) {
            return this.responseBuilder
                    .code( EntityResult.OPERATION_WRONG )
                    .message( MESSAGE_ERROR_INVALID_KEY_FOR_WORKSPACE )
                    .build();
        }

        return null;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| RUN |----------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void run() {
        final Integer maxKeys = 1;
        final ListObjectsRequest request = new ListObjectsRequest()
                .withBucketName( this.bucket )
                .withPrefix( this.key )
                .withMaxKeys( maxKeys );

        this.response = this.repository.download( request );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| RESPONSE |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult response() {
        if( this.response != null ) {
            final List<OSdmsS3RepositoryDto> data = this.response.getData();
            if( data.isEmpty() || data.size() > 1 || ! data.get( 0 ).getKey().equals( this.key ) || data.get(
                    0 ).getName().equals( OSdmsS3RepositoryDto.FILE_NAME_MARK_FOLDER ) ) {
                this.response.setCode( OSdmsS3RepositoryResponseCodes.NOT_FOUND );
                this.response.setMessage( MESSAGE_ERROR_NOT_FOUND );
            }
        }

        return this.responseMapper.map( this.response );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
