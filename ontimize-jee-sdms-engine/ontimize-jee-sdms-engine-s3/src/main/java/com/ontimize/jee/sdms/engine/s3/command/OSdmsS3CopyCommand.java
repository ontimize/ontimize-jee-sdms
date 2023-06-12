package com.ontimize.jee.sdms.engine.s3.command;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.sdms.common.command.IOSdmsCommand;
import com.ontimize.jee.sdms.common.inyector.IOSdmsInyector;
import com.ontimize.jee.sdms.common.response.builder.IOSdmsResponseBuilder;
import com.ontimize.jee.sdms.engine.s3.repository.IOSdmsS3Repository;
import com.ontimize.jee.sdms.engine.s3.repository.OSdmsS3RepositoryProxy;
import com.ontimize.jee.sdms.engine.s3.repository.dto.OSdmsS3RepositoryDto;
import com.ontimize.jee.sdms.engine.s3.repository.response.OSdmsS3RepositoryResponse;
import com.ontimize.jee.sdms.engine.s3.util.config.IOSdmsS3EngineConfig;
import com.ontimize.jee.sdms.engine.s3.util.input.data.OSdmsS3InputData;
import com.ontimize.jee.sdms.engine.s3.util.input.data.reader.IOSdmsS3DataReader;
import com.ontimize.jee.sdms.engine.s3.util.input.filter.OSdmsS3InputFilter;
import com.ontimize.jee.sdms.engine.s3.util.input.filter.reader.IOSdmsS3FilterReader;
import com.ontimize.jee.sdms.common.path.validator.IOSdmsPathValidator;
import com.ontimize.jee.sdms.engine.s3.util.response.mapper.IOSdmsS3ResponseMapper;
import com.ontimize.jee.sdms.common.workspace.OSdmsWorkspace;
import com.ontimize.jee.sdms.common.workspace.manager.IOSdmsWorkspaceManager;

import java.util.ArrayList;
import java.util.List;



/**
 * Command to copy S3 objects to another S3 prefix
 */
public class OSdmsS3CopyCommand implements IOSdmsCommand {

    //Messages
    private static final String MESSAGE_ERROR_NO_ACTIVE_WORKSPACE = "No active Workspace found";
    private static final String MESSAGE_ERROR_NO_KEYS = "No valid S3 keys found";
    private static final String MESSAGE_ERROR_NO_DESTINATION = "The prefix from which the action is performed in order to copy the resources has not been found.";
    private static final String MESSAGE_ERROR_INVALID_DESTINATION_KEY_FOR_WORKSPACE = "The destination S3 key is invalid for the selected Workspace";
    private static final String MESSAGE_ERROR_INVALID_DESTINATION_PREFIX_FOR_WORKSPACE = "The destination S3 prefix is invalid for the selected Workspace";
    private static final String MESSAGE_ERROR_NO_CURRENT_PREFIX = "The current prefix to copy the resources has not been found.";


    // Dependencies
    private IOSdmsS3Repository repository;
    private IOSdmsS3EngineConfig s3EngineConfig;
    private IOSdmsWorkspaceManager workspaceManager;
    private OSdmsWorkspace workspace;
    private IOSdmsResponseBuilder responseBuilder;
    private IOSdmsS3ResponseMapper responseMapper;
    private IOSdmsPathValidator pathValidator;
    private IOSdmsS3FilterReader filterReader;
    private IOSdmsS3DataReader dataReader;



    //Data
    private String bucket;
    private OSdmsS3InputFilter filter;
    private OSdmsS3InputData data;
    private List<String> keys;
    private String destinationKey;
    private String destinationPrefix;
    private String currentPrefix;



    //Response
    private OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> response;

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| ENTRYPOINT |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3CopyCommand(final OSdmsS3InputFilter filter, final OSdmsS3InputData data ) {
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
        this.s3EngineConfig = inyector.get( IOSdmsS3EngineConfig.class );
        this.workspaceManager = inyector.get( IOSdmsWorkspaceManager.class );
        this.responseBuilder = inyector.get( IOSdmsResponseBuilder.class );
        this.responseMapper = inyector.get( IOSdmsS3ResponseMapper.class );
        this.pathValidator = inyector.get( IOSdmsPathValidator.class );
        this.filterReader = inyector.get( IOSdmsS3FilterReader.class );
        this.dataReader = inyector.get( IOSdmsS3DataReader.class );

        //Get Data
        this.workspaceManager.active( filter.getWorkspace(), filter.getData() );
        this.workspace = workspaceManager.getActive();
        this.bucket = this.s3EngineConfig.getBucket();
        this.keys = this.filterReader.readAllKeys( this.filter );
        this.destinationKey = this.dataReader.readKey( this.data );
        this.destinationPrefix = this.dataReader.readPrefix( this.data );
        this.currentPrefix = this.dataReader.readCurrentPrefix( this.data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| VALIDATE |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult validate() {

        if( this.workspaceManager.getActive() == null ){
            return this.responseBuilder
                    .code( EntityResult.OPERATION_WRONG )
                    .message(MESSAGE_ERROR_NO_ACTIVE_WORKSPACE)
                    .build();
        }

        if( this.keys == null || this.keys.isEmpty() ) {
            return this.responseBuilder
                    .code( EntityResult.OPERATION_WRONG )
                    .message(MESSAGE_ERROR_NO_KEYS)
                    .build();
        }

        if( this.destinationKey == null && this.destinationPrefix == null ) {
            return this.responseBuilder
                    .code( EntityResult.OPERATION_WRONG )
                    .message(MESSAGE_ERROR_NO_DESTINATION)
                    .build();
        }

        if( this.destinationKey != null && !this.pathValidator.validate( this.destinationKey, this.workspace.getPatterns() ) ) {
            return this.responseBuilder
                    .code( EntityResult.OPERATION_WRONG )
                    .message(MESSAGE_ERROR_INVALID_DESTINATION_KEY_FOR_WORKSPACE)
                    .build();
        }

        if( this.destinationKey == null && this.destinationPrefix != null ){
            if( !this.pathValidator.validate( this.destinationPrefix, this.workspace.getPatterns() ) ) {
                return this.responseBuilder
                        .code(EntityResult.OPERATION_WRONG)
                        .message(MESSAGE_ERROR_INVALID_DESTINATION_PREFIX_FOR_WORKSPACE)
                        .build();
            }

            if( this.currentPrefix == null ) {
                return this.responseBuilder
                        .code(EntityResult.OPERATION_WRONG)
                        .message(MESSAGE_ERROR_NO_CURRENT_PREFIX)
                        .build();
            }
        }

        return null;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| RUN |----------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void run() {
        final List<ListObjectsRequest> requests = new ArrayList<>();
        this.keys.forEach( target -> {
            final ListObjectsRequest request = new ListObjectsRequest()
                    .withBucketName( this.bucket )
                    .withPrefix( target );
            requests.add( request );
        });

        if( this.destinationKey != null ) {
            final ListObjectsRequest request = requests.get( 0 );
            this.response = this.repository.copy( request, this.bucket, this.destinationKey );
        }
        else if ( this.destinationPrefix != null && this.currentPrefix != null ) {
            this.response = this.repository.copy( requests, this.bucket, this.destinationPrefix, this.currentPrefix );
        }
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
