package com.ontimize.jee.sdms.common.event.listener;

import com.ontimize.jee.sdms.common.event.data.IOSdmsEventData;

import java.util.EventListener;
import java.util.List;


/**
 * The interface sets the logic to be executed when a certain event is triggered.
 *
 * @param <T> The data type associated with the event.
 *
 * @see IOSdmsEventData
 * @see EventListener
 */
public interface IOSdmsEventListener<T extends IOSdmsEventData> extends EventListener {

    /**
     * Gets the list of events that this listener is registered to.
     *
     * @return The list of events.
     */
    List<Enum> getEvents();


    /**
     * Defines the behavior of this listener when the associated event is triggered.
     *
     * @param data The data associated with the event.
     */
    void run( T data );

}
