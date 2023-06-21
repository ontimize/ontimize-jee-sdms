package com.ontimize.jee.sdms.common.inyector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 * Implementation of {@link IOSdmsInyector}.
 *
 * @see IOSdmsInyector
 */
@Component( "oSdmsInyector" )
public class OSdmsInyector implements IOSdmsInyector {

    /** The context with the dependencies. */
    private @Autowired ApplicationContext context;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsInyector() {
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public Object get( final String name ) {
        return this.context.getBean( name );
    }

    @Override
    public <T> T get( final Class<T> clazz ) {
        return this.context.getBean( clazz );
    }

    @Override
    public <T> T get( final String name, final Class<T> clazz ) {
        return this.context.getBean( name, clazz );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
