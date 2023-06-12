package com.ontimize.jee.sdms.common.command.handler;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.sdms.common.command.IOSdmsCommand;


/**
 * Interface to define a handler to execute a command in the DMS engine.
 * @see IOSdmsCommand
 */
public interface IOSdmsCommandHandler {

    /**
     * Method to execute a command.
     *
     * @param command The command to execute.
     *
     * @return The result of the command.
     *
     * @see IOSdmsCommand
     * @see EntityResult
     */
    EntityResult run( IOSdmsCommand command );

}
