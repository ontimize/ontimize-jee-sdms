package com.ontimize.jee.sdms.engine.s3.command;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.sdms.common.command.IOSdmsCommand;
import com.ontimize.jee.sdms.common.inyector.IOSdmsInyector;
import com.ontimize.jee.sdms.common.path.validator.IOSdmsPathValidator;
import com.ontimize.jee.sdms.common.response.builder.IOSdmsResponseBuilder;
import com.ontimize.jee.sdms.common.workspace.OSdmsWorkspace;
import com.ontimize.jee.sdms.common.workspace.manager.IOSdmsWorkspaceManager;
import com.ontimize.jee.sdms.engine.s3.repository.IOSdmsS3Repository;
import com.ontimize.jee.sdms.engine.s3.repository.OSdmsS3RepositoryProxy;
import com.ontimize.jee.sdms.engine.s3.repository.dto.OSdmsS3RepositoryDto;
import com.ontimize.jee.sdms.engine.s3.repository.response.OSdmsS3RepositoryResponse;
import com.ontimize.jee.sdms.engine.s3.util.config.IOSdmsS3EngineConfig;
import com.ontimize.jee.sdms.engine.s3.util.input.data.OSdmsS3InputData;
import com.ontimize.jee.sdms.engine.s3.util.input.data.reader.IOSdmsS3DataReader;
import com.ontimize.jee.sdms.engine.s3.util.input.filter.OSdmsS3InputFilter;
import com.ontimize.jee.sdms.engine.s3.util.normalize.IOSdmsS3KeyNormalize;
import com.ontimize.jee.sdms.engine.s3.util.response.mapper.IOSdmsS3ResponseMapper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


/**
 * Command to create a new object in the S3 repository
 */
public class OSdmsS3CreateCommand implements IOSdmsCommand {

    //Messages
    private static final String MESSAGE_ERROR_NO_ACTIVE_WORKSPACE = "No active workspace found";
    private static final String MESSAGE_ERROR_NO_PREFIX = "No valid S3 prefix found";
    private static final String MESSAGE_ERROR_INVALID_PREFIX_FOR_WORKSPACE = "The S3 prefix is invalid for the selected workspace";


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
    private OSdmsS3InputData data;
    private String prefix;


    //Response
    private OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> response;

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| ENTRYPOINT |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3CreateCommand( final OSdmsS3InputFilter filter, final OSdmsS3InputData data ) {
        this.filter = filter;
        this.data = data;
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
        final IOSdmsS3DataReader dataReader = inyector.get( IOSdmsS3DataReader.class );
        final IOSdmsS3EngineConfig s3EngineConfig = inyector.get( IOSdmsS3EngineConfig.class );
        final IOSdmsS3KeyNormalize keyNormalize = inyector.get( IOSdmsS3KeyNormalize.class );

        //Get Data
        this.workspaceManager.active( filter.getWorkspace(), filter.getData() );
        this.workspace = workspaceManager.getActive();
        this.bucket = s3EngineConfig.getBucket();
        this.prefix = dataReader.readPrefix( this.data );
        if( this.prefix == null ) this.prefix = dataReader.readKey( this.data );
        this.prefix = keyNormalize.normalize( this.prefix );
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

        if( this.prefix == null ) {
            return this.responseBuilder
                    .code( EntityResult.OPERATION_WRONG )
                    .message( MESSAGE_ERROR_NO_PREFIX )
                    .build();
        }

        if( ! this.pathValidator.validate( this.prefix, this.workspace.getPatterns() ) ) {
            return this.responseBuilder
                    .code( EntityResult.OPERATION_WRONG )
                    .message( MESSAGE_ERROR_INVALID_PREFIX_FOR_WORKSPACE )
                    .build();
        }

        return null;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| RUN |----------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void run() {
        if( ! this.prefix.endsWith( "/" ) ) this.prefix = this.prefix.concat( "/" );
        final String name = this.prefix.concat( OSdmsS3RepositoryDto.FILE_NAME_MARK_FOLDER );
        final InputStream inputStream = new ByteArrayInputStream( ( new byte[ 0 ] ) );
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength( 0 );

        final PutObjectRequest request = new PutObjectRequest( this.bucket, name, inputStream, metadata );
        if( this.data.hasMetadata() ) this.data.getMetadata().forEach( metadata::addUserMetadata );

        this.response = this.repository.upload( request );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| RESPONSE |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult response() {
        return this.responseMapper.map( this.response );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
