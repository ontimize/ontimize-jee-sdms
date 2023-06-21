package com.ontimize.jee.sdms.common.response.builder;

import java.util.Map;


/**
 * Interface to define a mappeable object.
 */
public interface IOSdmsMappeable {

    /**
     * Returns the object information as a map.
     *
     * @return The object information as a map
     */
    Map<?, ?> toMap();

}
