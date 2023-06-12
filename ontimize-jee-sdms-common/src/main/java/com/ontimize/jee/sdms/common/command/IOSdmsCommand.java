package com.ontimize.jee.sdms.common.command;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.sdms.common.inyector.IOSdmsInyector;


/**
 * Interface to define a command to be executed in the DMS engine.
 */
public interface IOSdmsCommand {

    /**
     * Method to initialize the command.
     *
     * @param inyector The inyector to use to inject dependencies in the command.
     *
     * @see IOSdmsInyector
     */
    default void init( IOSdmsInyector inyector ){}


    /**
     * Method to validate the command.
     *
     * @return The validation result. Null if the command is valid or an EntityResult with the validation errors with
     * the information to show to the user.
     *
     * @see EntityResult
     */
    default EntityResult validate(){ return null; }


    /**
     * Method that is executed before the command is executed.
     */
    default void before(){}


    /**
     * Method that executes the command.
     */
    void run();



    /**
     * Method that is executed before the command is executed.
     */
    default void after(){}


    /**
     * Method that obtains the result of the command.
     *
     * @return The EntityResult with the result of the command.
     *
     * @see EntityResult
     */
    EntityResult response();
}
