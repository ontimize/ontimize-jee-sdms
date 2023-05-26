package com.ontimize.jee.sdms.common.event.data;



/**
 * The IOSdmsEventData interface sets the necessary information related to an event.
 */
public interface IOSdmsEventData {

    /**
     * Sets the event associated with the data.
     *
     * @param event The event that triggered.
     */
    void setEvent( Enum event );

}
