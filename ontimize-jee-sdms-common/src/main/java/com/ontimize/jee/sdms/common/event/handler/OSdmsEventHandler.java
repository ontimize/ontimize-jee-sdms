package com.ontimize.jee.sdms.common.event.handler;

import com.ontimize.jee.sdms.common.event.IOSdmsEvent;
import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;


/**
 * The OSdmsEventHandler class implements the {@link IOSdmsEventHandler} interface and provides the basic functionality
 * to handle events in the Ontimize framework.
 *
 * @see IOSdmsEventHandler
 */
public class OSdmsEventHandler implements IOSdmsEventHandler {

    /**
     * The LOGGER constant, which is an instance of org.slf4j.Logger used for logging events and diagnostic messages
     * during program execution.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsEventHandler.class );


    /** Map to store events as keys and listeners as values. */
    private final Map<Class<? extends IOSdmsEvent>, Set<IOSdmsEventListener>> events = new HashMap<>();

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsEventHandler() {
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void addEventListener( final IOSdmsEventListener... listeners ) {
        for( final IOSdmsEventListener listener : listeners ) {
            this.addEventListenerForEachEvent( listener );
        }
    }


    @Override
    public void removeEventListener( final IOSdmsEventListener... listeners ) {
        for( final IOSdmsEventListener listener : listeners ) {
            this.removeEventListenerForEachEvent( listener );
        }
    }


    @Override
    public <E extends IOSdmsEvent> void clearEvent( final Class<E>... events ) {
        for( final Class<E> event : events ) {
            this.events.remove( event );
        }
    }


    @Override
    public void trigger( final IOSdmsEvent event ) {
        if( ! this.events.isEmpty() && this.events.containsKey( event.getClass() ) ) {
            this.events.get( event.getClass() ).stream().forEach( target -> target.run( event ) );
        }
    }


    @Override
    public void trigger( final List<IOSdmsEvent> events ) {
        events.stream().forEach( target -> this.trigger( target ) );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| UTILITIES |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\


    /**
     * Adds the specified event listener to each associated event.
     *
     * @param listener The event listener to add.
     *
     * @see IOSdmsEventListener
     */
    private void addEventListenerForEachEvent( final IOSdmsEventListener listener ) {
            this.events.computeIfAbsent( listener.getEvent(), key -> new HashSet<>() );
            this.events.get( listener.getEvent() ).add( listener );
    }


    /**
     * Removes the specified listener from all events it is currently registered to.
     *
     * @param listener The listener to remove.
     *
     * @see IOSdmsEventListener
     */
    private void removeEventListenerForEachEvent( final IOSdmsEventListener listener ) {
        final Set<IOSdmsEventListener> registerListeners = this.events.get( listener.getEvent() );
        final Set<IOSdmsEventListener> registerListenersIntoEvent = registerListeners.stream()
                .filter( target -> target == listener )
                .collect( Collectors.toSet() );

        registerListenersIntoEvent.stream().forEach( registerListeners::remove );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}

