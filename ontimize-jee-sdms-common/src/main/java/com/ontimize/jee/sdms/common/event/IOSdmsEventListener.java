package com.ontimize.jee.sdms.common.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EventListener;



public abstract class IOSdmsEventListener<E extends IOSdmsEvent> implements EventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger( IOSdmsEventListener.class );

    public abstract Class<E> getEvent();

    public void run( final E event ) {
        LOGGER.debug( "{}", event );
    }

}
