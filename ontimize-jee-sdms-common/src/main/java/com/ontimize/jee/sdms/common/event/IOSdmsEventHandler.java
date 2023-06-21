package com.ontimize.jee.sdms.common.event;

import com.ontimize.jee.sdms.common.event.data.IOSdmsEventData;
import com.ontimize.jee.sdms.common.event.listener.IOSdmsEventListener;

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
    void clearEvent( Enum... events );


    /**
     * Triggers the specified event with the given data.
     *
     * @param event The event to trigger.
     * @param data  The data to pass to the event listeners.
     *
     * @see IOSdmsEventData
     */
    void trigger( Enum event, IOSdmsEventData data );


    /**
     * Triggers the specified list of events with the given data.
     *
     * @param events The list of events to trigger.
     * @param data   The data to pass to the event listeners.
     *
     * @see IOSdmsEventData
     */
    void trigger( List<Enum> events, IOSdmsEventData data );

}

