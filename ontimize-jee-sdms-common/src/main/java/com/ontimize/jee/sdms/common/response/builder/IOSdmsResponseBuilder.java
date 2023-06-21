package com.ontimize.jee.sdms.common.response.builder;

import com.ontimize.jee.common.dto.EntityResult;

import java.util.List;
import java.util.Map;


/**
 * Interface to define the methods to build a response from a DMS operation
 */
public interface IOSdmsResponseBuilder {

    /**
     * Sets the code of the response.
     *
     * @param code The code of the response
     *
     * @return The builder
     */
    IOSdmsResponseBuilder code( Integer code );


    /**
     * Sets the message of the response.
     *
     * @param message The message of the response
     *
     * @return The builder
     */
    IOSdmsResponseBuilder message( String message );


    /**
     * Builds the response.
     *
     * @return The response as an EntityResult
     */
    EntityResult build();


    /**
     * Builds the response from the data.
     *
     * @param data The data to build the response
     *
     * @return The response as an EntityResult
     */
    EntityResult buildWithMap( Map data );


    /**
     * Builds the response from the data list.
     *
     * @param data The data list to build the response
     *
     * @return The response as an EntityResult
     */
    EntityResult buildWithMap( List<Map> data );


    /**
     * Builds the response from the mappeable data.
     *
     * @param data The mappeable data to build the response
     *
     * @return The response as an EntityResult
     */
    <T extends IOSdmsMappeable> EntityResult buildWithMappeable( T data );


    /**
     * Builds the response from the mappeable data list.
     *
     * @param data The mappeable data list to build the response
     *
     * @return The response as an EntityResult
     */
    <T extends IOSdmsMappeable> EntityResult buildWithMappeable( List<T> data );
}
