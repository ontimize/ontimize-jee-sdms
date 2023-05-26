package com.ontimize.jee.sdms.server.service;


import com.ontimize.jee.sdms.common.action.IOSdmsAction;
import com.ontimize.jee.sdms.common.engine.IOSdmsEngine;

/**
 * This interface represents the DMS Service.
 *
 * @see IOSdmsEngine
 */
public interface IOSdmsService extends IOSdmsAction {

    //Parameters Names Constants
    String PARAM_ID = "id";
    String PARAM_FILE = "file";
    String PARAM_DATA = "data";



    /**
     * Sets the DMS Engine to be used by the DMS Service.
     *
     * @param engine The DMS Engine to be used by the DMS Service.
     */
    void setEngine( IOSdmsEngine engine );

}
