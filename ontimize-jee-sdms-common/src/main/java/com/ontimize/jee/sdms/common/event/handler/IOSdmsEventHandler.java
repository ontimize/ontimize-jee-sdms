package com.ontimize.jee.sdms.common.event.handler;

import com.ontimize.jee.sdms.common.event.IOSdmsEvent;
import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;

import java.util.List;


/**
 * The IOSdmsEventHandler interface provides methods to manage event listeners.
 */
public interface IOSdmsEventHandler {

    /**
     * Adds the specified event listeners to this event handler.
     *
     * @param listeners The event listeners to add.
     *
     * @see IOSdmsEventListener
     */
    void addEventListener( IOSdmsEventListener... listeners );


    /**
     * Removes the specified event listeners from this event handler.
     *
     * @param listeners The event listeners to remove.
     *
     * @see IOSdmsEventListener
     */
    void removeEventListener( IOSdmsEventListener... listeners );


    /**
     * Deletes the eventlisteners of a given event from this event handler.
     *
     * @param events The events from which event listeners are to be removed.
     */
    <E extends IOSdmsEvent> void clearEvent( Class<E>... events );


    /**
     * Triggers the specified event.
     *
     * @param event The event to trigger.
     */
    void trigger( IOSdmsEvent event );


    /**
     * Triggers the specified list of events.
     *
     * @param events The list of events to trigger.
     */
    void trigger( List<IOSdmsEvent> events );

}

