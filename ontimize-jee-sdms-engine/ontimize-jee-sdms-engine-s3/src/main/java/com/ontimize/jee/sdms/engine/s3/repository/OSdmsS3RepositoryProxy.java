package com.ontimize.jee.sdms.engine.s3.repository;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ontimize.jee.sdms.common.event.handler.IOSdmsEventHandler;
import com.ontimize.jee.sdms.engine.s3.repository.dto.OSdmsS3RepositoryDto;
import com.ontimize.jee.sdms.engine.s3.repository.event.*;
import com.ontimize.jee.sdms.engine.s3.repository.response.OSdmsS3RepositoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Implementation of {@link IOSdmsS3Repository}. This class represents the repository proxy, which will perform extra
 * actions before performing the task. In this case it will trigger the events of each action via the Ontimize
 * EventHandler.
 *
 * @see IOSdmsS3Repository
 */
@Repository( "OSdmsS3RepositoryProxy" )
public class OSdmsS3RepositoryProxy implements IOSdmsS3Repository {


    /** The Ontimize EventHandler */
    private @Autowired IOSdmsEventHandler eventHandler;


    /** The S3 Repository */
    @Qualifier( "OSdmsS3Repository" )
    private @Autowired IOSdmsS3Repository repository;

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| FIND |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> find( final ListObjectsRequest request ) {
        this.eventHandler.trigger( new S3RepositoryFindEvent( request ) );
        return this.repository.find( request );
    }

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> find( final List<ListObjectsRequest> requests ) {
        this.eventHandler.trigger( new S3RepositoryFindEvent( requests ) );
        return this.repository.find( requests );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DOWNLOAD |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> download( final ListObjectsRequest request ) {
        this.eventHandler.trigger( new S3RepositoryDownloadEvent( request ) );
        return this.repository.download( request );
    }

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> download( final List<ListObjectsRequest> requests ) {
        this.eventHandler.trigger( new S3RepositoryDownloadEvent( requests ) );
        return this.repository.download( requests );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| UPLOAD |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> upload( final PutObjectRequest request ) {
        this.eventHandler.trigger( new S3RepositoryUploadEvent( request ) );
        return this.repository.upload( request );
    }

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> upload( final List<PutObjectRequest> requests ) {
        this.eventHandler.trigger( new S3RepositoryUploadEvent( requests ) );
        return this.repository.upload( requests );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| COPY |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> copy( final ListObjectsRequest request, final String bucket, final String key ) {
        this.eventHandler.trigger( new S3RepositoryCopyEvent( request, bucket, key ) );
        return this.repository.copy( request, bucket, key );
    }

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> copyAll( final List<ListObjectsRequest> requests, final String bucket, final String prefix, final String currentPrefix ) {
        this.eventHandler.trigger( new S3RepositoryCopyAllEvent( requests, bucket, prefix, currentPrefix ) );
        return this.repository.copyAll( requests, bucket, prefix, currentPrefix );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| COPY |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> move( final ListObjectsRequest request, final String bucket, final String key ) {
        this.eventHandler.trigger( new S3RepositoryMoveEvent( request, bucket, key ) );
        return this.repository.move( request, bucket, key );
    }

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> moveAll( final List<ListObjectsRequest> requests, final String bucket, final String prefix, final String currentPrefix ) {
        this.eventHandler.trigger( new S3RepositoryMoveAllEvent( requests, bucket, prefix, currentPrefix ) );
        return this.repository.moveAll( requests, bucket, prefix, currentPrefix );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DELETE |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> delete( final ListObjectsRequest request ) {
        this.eventHandler.trigger( new S3RepositoryDeleteEvent( request ) );
        return this.repository.delete( request );
    }

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> delete( final List<ListObjectsRequest> requests ) {
        this.eventHandler.trigger( new S3RepositoryDeleteEvent( requests ) );
        return this.repository.delete( requests );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
}
