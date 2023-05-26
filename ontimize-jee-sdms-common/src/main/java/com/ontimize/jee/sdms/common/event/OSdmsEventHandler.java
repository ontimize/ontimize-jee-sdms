package com.ontimize.jee.sdms.common.event;

import com.ontimize.jee.sdms.common.event.data.IOSdmsEventData;
import com.ontimize.jee.sdms.common.event.listener.IOSdmsEventListener;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;



/**
 * The OSdmsEventHandler class implements the {@link IOSdmsEventHandler} interface and provides the basic
 * functionality to handle events in the Ontimize framework.
 *
 * @see IOSdmsEventHandler
 */
@NoArgsConstructor
public class OSdmsEventHandler implements IOSdmsEventHandler {

    /**
     * The LOGGER constant, which is an instance of org.slf4j.Logger used for logging events and diagnostic messages
     * during program execution.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsEventHandler.class );


    /** Map to store events as keys and listeners as values. */
    private final Map<Enum, Set<IOSdmsEventListener>> events = new HashMap<>();

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void addEventListener ( final IOSdmsEventListener... listeners ) {
        for ( final IOSdmsEventListener listener : listeners ) {
            this.addEventListenerForEachEvent ( listener );
        }
    }



    @Override
    public void removeEventListener ( final IOSdmsEventListener... listeners ) {
        for ( final IOSdmsEventListener listener : listeners ) {
            this.removeEventListenerForEachEvent ( listener );
        }
    }



    @Override
    public void clearEvent ( final Enum... events ) {
        for ( final Enum event : events ) {
            this.events.remove( event );
        }
    }



    @Override
    public void trigger ( final Enum event, final IOSdmsEventData data ) {
        if( !this.events.isEmpty() && this.events.containsKey( event ) ) {
            data.setEvent( event );
            this.events.get( event ).stream().forEach( target -> target.run( data ));
        }
    }


    @Override
    public void trigger ( final List<Enum> events, final IOSdmsEventData data ) {
        events.stream().forEach( target -> this.trigger( target, data ) );
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
    private void addEventListenerForEachEvent ( final IOSdmsEventListener listener ) {
        for ( final Object event : listener.getEvents() ) {
            this.events.computeIfAbsent( (Enum) event, key -> new HashSet<>() );
            this.events.get( event ).add( listener );
        }
    }



    /**
     * Removes the specified listener from all events it is currently registered to.
     *
     * @param listener The listener to remove.
     *
     * @see IOSdmsEventListener
     */
    private void removeEventListenerForEachEvent ( final IOSdmsEventListener listener ) {
        for ( final Object event : listener.getEvents() ) {
            final Set<IOSdmsEventListener> registerListeners = this.events.get( event );
            final Set<IOSdmsEventListener> registerListenersIntoEvent = registerListeners.stream()
                    .filter(target -> target == listener )
                    .collect(Collectors.toSet() );

            registerListenersIntoEvent.stream().forEach( registerListeners::remove );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}

