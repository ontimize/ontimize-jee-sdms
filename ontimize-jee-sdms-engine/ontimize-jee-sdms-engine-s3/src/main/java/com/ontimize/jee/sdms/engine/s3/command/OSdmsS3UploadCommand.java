package com.ontimize.jee.sdms.engine.s3.command;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

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
import com.ontimize.jee.sdms.common.path.validator.IOSdmsPathValidator;
import com.ontimize.jee.sdms.engine.s3.util.response.mapper.IOSdmsS3ResponseMapper;
import com.ontimize.jee.sdms.common.workspace.OSdmsWorkspace;
import com.ontimize.jee.sdms.common.workspace.manager.IOSdmsWorkspaceManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;


/**
 * Command to upload a file to S3
 */
public class OSdmsS3UploadCommand implements IOSdmsCommand {

    //Messages
    private static final String MESSAGE_ERROR_NO_ACTIVE_WORKSPACE = "No active Workspace found";
    private static final String MESSAGE_ERROR_NO_KEY = "No valid S3 key found";
    private static final String MESSAGE_ERROR_INVALID_KEY_FOR_WORKSPACE = "The S3 key is invalid for the selected Workspace";



    // Dependencies
    private IOSdmsS3Repository repository;
    private IOSdmsS3EngineConfig s3EngineConfig;
    private IOSdmsWorkspaceManager workspaceManager;
    private OSdmsWorkspace workspace;
    private IOSdmsResponseBuilder responseBuilder;
    private IOSdmsS3ResponseMapper responseMapper;
    private IOSdmsPathValidator pathValidator;
    private IOSdmsS3DataReader dataReader;



    //Data
    private String bucket;
    private OSdmsS3InputFilter filter;
    private OSdmsS3InputData data;
    private MultipartFile file;
    private String key;



    //Response
    private OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> response;

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| ENTRYPOINT |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3UploadCommand(final OSdmsS3InputFilter filter, final OSdmsS3InputData data, final MultipartFile file ) {
        this.filter = filter;
        this.data = data;
        this.file = file;
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
        this.dataReader = inyector.get( IOSdmsS3DataReader.class );

        //Get Data
        this.workspaceManager.active( filter.getWorkspace(), filter.getData() );
        this.workspace = workspaceManager.getActive();
        this.bucket = this.s3EngineConfig.getBucket();
        this.key = this.dataReader.readKey( this.data );
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

        if( this.key == null ) {
            return this.responseBuilder
                    .code( EntityResult.OPERATION_WRONG )
                    .message( MESSAGE_ERROR_NO_KEY )
                    .build();
        }

        if( !this.pathValidator.validate( this.key, this.workspace.getPatterns() ) ) {
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
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength( this.file.getSize() );

        //Create DateTimeFormatter to format the creation date
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern( "dd/MM/yyyy-HH:mm:ss" )
                .withLocale( Locale.getDefault() );

        try {
            final PutObjectRequest request = new PutObjectRequest(this.bucket, this.key, this.file.getInputStream(), metadata);

            if (!this.data.hasMetadata()) this.data.setMetadata( new HashMap<>() );
            final String creationDate = LocalDateTime.now().format( dateTimeFormatter );
            this.data.getMetadata().put( "creation_date", creationDate );
            this.data.getMetadata().forEach(metadata::addUserMetadata);

            this.response = this.repository.upload(request);
        }
        catch ( final IOException e ) {
            //Empty
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
