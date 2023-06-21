package com.ontimize.jee.sdms.engine.s3.repository;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ontimize.jee.sdms.common.event.IOSdmsEventHandler;
import com.ontimize.jee.sdms.common.event.data.OSdmsEventData;
import com.ontimize.jee.sdms.common.event.data.builder.IOSdmsEventDataBuilder;
import com.ontimize.jee.sdms.engine.s3.repository.dto.OSdmsS3RepositoryDto;
import com.ontimize.jee.sdms.engine.s3.repository.request.OSdmsS3RepositorySimpleRequest;
import com.ontimize.jee.sdms.engine.s3.repository.response.OSdmsS3RepositoryResponse;
import com.ontimize.jee.sdms.event.OSdmsS3RepositoryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * The LOGGER constant, which is an instance of org.slf4j.Logger used for logging events and diagnostic messages
     * during program execution.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsS3RepositoryProxy.class );

    /** The Event Data builder */
    private @Autowired IOSdmsEventDataBuilder eventDataBuilder;


    /** The Ontimize EventHandler */
    private @Autowired IOSdmsEventHandler eventHandler;


    /** The S3 Repository */
    @Qualifier( "OSdmsS3Repository" )
    private @Autowired IOSdmsS3Repository repository;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3RepositoryProxy() {
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| FIND |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> find( final ListObjectsRequest request ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_REQUEST, request )
                .build();

        this.eventHandler.trigger( OSdmsS3RepositoryEvent.FIND, eventData );
        return this.repository.find( request );
    }

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> find( final List<ListObjectsRequest> requests ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_REQUEST, requests )
                .build();

        this.eventHandler.trigger( OSdmsS3RepositoryEvent.FIND, eventData );
        return this.repository.find( requests );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DOWNLOAD |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> download( final ListObjectsRequest request ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_REQUEST, request )
                .build();

        this.eventHandler.trigger( OSdmsS3RepositoryEvent.GET_METADATA, eventData );
        return this.repository.download( request );
    }

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> download( final List<ListObjectsRequest> requests ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_REQUEST, requests )
                .build();

        this.eventHandler.trigger( OSdmsS3RepositoryEvent.GET_METADATA, eventData );
        return this.repository.download( requests );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| UPLOAD |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> upload( final PutObjectRequest request ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_REQUEST, request )
                .build();

        this.eventHandler.trigger( OSdmsS3RepositoryEvent.UPLOAD, eventData );
        return this.repository.upload( request );
    }

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> upload( final List<PutObjectRequest> requests ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_REQUEST, requests )
                .build();

        this.eventHandler.trigger( OSdmsS3RepositoryEvent.UPLOAD, eventData );
        return this.repository.upload( requests );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| COPY |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> copy( final ListObjectsRequest request, String bucket, String key ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_REQUEST, request )
                .input( PARAM_BUCKET, bucket )
                .input( PARAM_KEY, key )
                .build();

        this.eventHandler.trigger( OSdmsS3RepositoryEvent.COPY, eventData );
        return this.repository.copy( request, bucket, key );
    }

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> copy( final List<ListObjectsRequest> requests, String bucket, String prefix, String currentPrefix ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_REQUEST, requests )
                .input( PARAM_BUCKET, bucket )
                .input( PARAM_PREFIX, prefix )
                .input( PARAM_CURRENT_PREFIX, currentPrefix )
                .build();

        this.eventHandler.trigger( OSdmsS3RepositoryEvent.COPY, eventData );
        return this.repository.copy( requests, bucket, prefix, currentPrefix );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| COPY |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> move( final ListObjectsRequest request, String bucket, String key ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_REQUEST, request )
                .input( PARAM_BUCKET, bucket )
                .input( PARAM_KEY, key )
                .build();

        this.eventHandler.trigger( OSdmsS3RepositoryEvent.MOVE, eventData );
        return this.repository.move( request, bucket, key );
    }

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> move( final List<ListObjectsRequest> requests, String bucket, String prefix, String currentPrefix ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_REQUEST, requests )
                .input( PARAM_BUCKET, bucket )
                .input( PARAM_PREFIX, prefix )
                .input( PARAM_CURRENT_PREFIX, currentPrefix )
                .build();

        this.eventHandler.trigger( OSdmsS3RepositoryEvent.MOVE, eventData );
        return this.repository.move( requests, bucket, prefix, currentPrefix );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DELETE |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> delete( final ListObjectsRequest request ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_REQUEST, request )
                .build();

        this.eventHandler.trigger( OSdmsS3RepositoryEvent.DELETE, eventData );
        return this.repository.delete( request );
    }

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> delete( final List<ListObjectsRequest> requests ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_REQUEST, requests )
                .build();

        this.eventHandler.trigger( OSdmsS3RepositoryEvent.DELETE, eventData );
        return this.repository.delete( requests );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| EXISTS |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<Boolean> exists( OSdmsS3RepositorySimpleRequest request ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_REQUEST, request )
                .build();

        this.eventHandler.trigger( OSdmsS3RepositoryEvent.EXISTS, eventData );
        return this.repository.exists( request );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
}
