package com.ontimize.jee.sdms.common.event.data.builder;


import com.ontimize.jee.sdms.common.event.data.OSdmsEventData;

/**
 * The Interface IOSdmsLoggerEventDataBuilder that defines the methods to build the event data.
 */
public interface IOSdmsEventDataBuilder {

    /**
     * Sets the source from the event it's triggered.
     *
     * @param source The source from the event it's triggered.
     *
     * @return The builder.
     */
    IOSdmsEventDataBuilder source( Class<?> source );


    /**
     * Sets the input data from the event it's triggered.
     *
     * @param key   The key enum of the input data.
     * @param value The value of the input data.
     *
     * @return The builder.
     */
    IOSdmsEventDataBuilder input( Enum key, Object value );


    /**
     * from the event it's triggered.
     *
     * @param key   The key string of the input data.
     * @param value The value of the input data.
     *
     * @return The builder.
     */
    IOSdmsEventDataBuilder input( String key, Object value );


    /**
     * Builds the event data.
     *
     * @return The event data.
     */
    OSdmsEventData build();
}
