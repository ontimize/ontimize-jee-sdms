package com.ontimize.jee.sdms.server.service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.sdms.common.dto.OSdmsRestDataDto;
import com.ontimize.jee.sdms.common.event.IOSdmsEventHandler;
import com.ontimize.jee.sdms.common.event.data.OSdmsEventData;
import com.ontimize.jee.sdms.common.event.data.builder.IOSdmsEventDataBuilder;
import com.ontimize.jee.sdms.common.engine.IOSdmsEngine;
import com.ontimize.jee.sdms.event.OSdmsServiceEvent;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;


/**
 * Implementation of the {@link IOSdmsService} interface.
 * Also, it implements the {@link InitializingBean} interface.
 *
 * @see IOSdmsService
 * @see InitializingBean
 */
@NoArgsConstructor
@Component( "OSdmsService" )
public class OSdmsService implements IOSdmsService {

    /** The Event Data builder */
    private @Autowired IOSdmsEventDataBuilder eventDataBuilder;

    /** The Ontimize EventHandler */
    private @Autowired IOSdmsEventHandler eventHandler;

    private @Autowired IOSdmsEngine engine;

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void setEngine( final IOSdmsEngine engine ){
        this.engine = engine;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - FIND |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult findById( final Serializable id, final OSdmsRestDataDto data ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_ID, id )
                .input( PARAM_DATA, data )
                .build();

        this.eventHandler.trigger( OSdmsServiceEvent.FIND_BY_ID, eventData );
        return this.engine.findById( id, data );
    }

    @Override
    public EntityResult find( final OSdmsRestDataDto data ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_DATA, data )
                .build();

        this.eventHandler.trigger( OSdmsServiceEvent.FIND, eventData );
        return this.engine.find( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - DOWNLOAD |----------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult downloadById( final Serializable id, final OSdmsRestDataDto data ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_ID, id )
                .input( PARAM_DATA, data )
                .build();

        this.eventHandler.trigger( OSdmsServiceEvent.DOWNLOAD_BY_ID, eventData );
        return this.engine.downloadById( id, data );
    }

    @Override
    public EntityResult download( final OSdmsRestDataDto data ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_DATA, data )
                .build();

        this.eventHandler.trigger( OSdmsServiceEvent.DOWNLOAD, eventData );
        return this.engine.download( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - UPLOAD |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult upload(final OSdmsRestDataDto data, final MultipartFile file ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_DATA, data )
                .input( PARAM_FILE, file )
                .build();

        this.eventHandler.trigger( OSdmsServiceEvent.UPLOAD, eventData );
        return this.engine.upload( data, file );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - CREATE |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult create( final OSdmsRestDataDto data ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_DATA, data )
                .build();

        this.eventHandler.trigger( OSdmsServiceEvent.CREATE, eventData );
        return this.engine.create( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - UPDATE |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult update( final OSdmsRestDataDto data ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_DATA, data )
                .build();

        this.eventHandler.trigger( OSdmsServiceEvent.UPDATE, eventData );
        return this.engine.update( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - COPY |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult copy( final OSdmsRestDataDto data ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_DATA, data )
                .build();

        this.eventHandler.trigger( OSdmsServiceEvent.COPY, eventData );
        return this.engine.copy( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - MOVE |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult move( final OSdmsRestDataDto data ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_DATA, data )
                .build();

        this.eventHandler.trigger( OSdmsServiceEvent.MOVE, eventData );
        return this.engine.move( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - DELETE |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult deleteById( final Serializable id, final OSdmsRestDataDto data ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_ID, id )
                .input( PARAM_DATA, data )
                .build();

        this.eventHandler.trigger( OSdmsServiceEvent.DELETE_BY_ID, eventData );
        return this.engine.deleteById( id, data );
    }

    @Override
    public EntityResult delete( final OSdmsRestDataDto data ) {
        final OSdmsEventData eventData = this.eventDataBuilder
                .source( this.getClass() )
                .input( PARAM_DATA, data )
                .build();

        this.eventHandler.trigger( OSdmsServiceEvent.DELETE, eventData );
        return this.engine.delete( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
