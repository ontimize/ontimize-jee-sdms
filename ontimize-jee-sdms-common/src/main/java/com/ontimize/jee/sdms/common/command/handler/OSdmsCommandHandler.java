package com.ontimize.jee.sdms.common.command.handler;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.sdms.common.command.IOSdmsCommand;
import com.ontimize.jee.sdms.common.inyector.IOSdmsInyector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Class to implementes a IOSdmsCommandHandler.
 *
 * @see IOSdmsCommandHandler
 */
@Component( "OSdmsCommandHandler" )
public class OSdmsCommandHandler implements IOSdmsCommandHandler {

    /** The inyector to use to inject dependencies in the command. */
    private @Autowired IOSdmsInyector inyector;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsCommandHandler() {
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Method to execute a command in following steps: 1- init 2- validate 3- before 4- run 5- after 6- response
     *
     * @param command The command to execute.
     *
     * @return The result of the command.
     */
    @Override
    public EntityResult run( final IOSdmsCommand command ) {
        //Init
        command.init( this.inyector );

        //Validate
        final EntityResult validationResult = command.validate();
        if( validationResult != null ) {
            return validationResult;
        }

        //Run
        command.before();
        command.run();
        command.after();

        //Response
        return command.response();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
