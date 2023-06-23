package com.ontimize.jee.sdms.server.service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.sdms.common.dto.OSdmsRestDataDto;
import com.ontimize.jee.sdms.common.engine.IOSdmsEngine;
import com.ontimize.jee.sdms.common.event.handler.IOSdmsEventHandler;
import com.ontimize.jee.sdms.server.service.event.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;


/**
 * Implementation of the {@link IOSdmsService} interface. Also, it implements the {@link InitializingBean} interface.
 *
 * @see IOSdmsService
 * @see InitializingBean
 */
@Component( "OSdmsService" )
public class OSdmsService implements IOSdmsService {

    /** The Ontimize EventHandler */
    private @Autowired IOSdmsEventHandler eventHandler;

    private @Autowired IOSdmsEngine engine;

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void setEngine( final IOSdmsEngine engine ) {
        this.engine = engine;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - FIND |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult findById( final Serializable id, final OSdmsRestDataDto data ) {
        this.eventHandler.trigger( new OSdmsServiceFindByIdEvent( id, data ) );
        return this.engine.findById( id, data );
    }

    @Override
    public EntityResult find( final OSdmsRestDataDto data ) {
        this.eventHandler.trigger( new OSdmsServiceFindEvent( data ) );
        return this.engine.find( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - DOWNLOAD |----------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult downloadById( final Serializable id, final OSdmsRestDataDto data ) {
        this.eventHandler.trigger( new OSdmsServiceDownloadByIdEvent( id, data ) );
        return this.engine.downloadById( id, data );
    }

    @Override
    public EntityResult download( final OSdmsRestDataDto data ) {
        this.eventHandler.trigger( new OSdmsServiceDownloadEvent( data ) );
        return this.engine.download( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - UPLOAD |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult upload( final OSdmsRestDataDto data, final MultipartFile file ) {
        this.eventHandler.trigger( new OSdmsServiceUploadEvent( data, file ) );
        return this.engine.upload( data, file );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - CREATE |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult create( final OSdmsRestDataDto data ) {
        this.eventHandler.trigger( new OSdmsServiceCreateEvent( data ) );
        return this.engine.create( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - UPDATE |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult update( final OSdmsRestDataDto data ) {
        this.eventHandler.trigger( new OSdmsServiceUpdateEvent( data ) );
        return this.engine.update( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - COPY |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult copy( final OSdmsRestDataDto data ) {
        this.eventHandler.trigger( new OSdmsServiceCopyEvent( data ) );
        return this.engine.copy( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - MOVE |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult move( final OSdmsRestDataDto data ) {
        this.eventHandler.trigger( new OSdmsServiceMoveEvent( data ) );
        return this.engine.move( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - DELETE |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult deleteById( final Serializable id, final OSdmsRestDataDto data ) {
        this.eventHandler.trigger( new OSdmsServiceDeleteByIdEvent( id, data ) );
        return this.engine.deleteById( id, data );
    }

    @Override
    public EntityResult delete( final OSdmsRestDataDto data ) {
        this.eventHandler.trigger( new OSdmsServiceDeleteEvent( data ) );
        return this.engine.delete( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
