package com.ontimize.jee.sdms.event.config;

import com.ontimize.jee.sdms.common.event.IOSdmsEventHandler;
import com.ontimize.jee.sdms.common.event.OSdmsEventHandler;
import com.ontimize.jee.sdms.common.event.listener.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * This class is a Spring configuration class that defines a bean for an Ontimize event handler. It uses the Ontimize
 * EventHandler implementation to handle Ontimize events. The handler uses a list of Ontimize Event Listeners to execute
 * specific behavior when an event is triggered.
 */
@Configuration
public class OSdmsEventHandlerConfig {

    /**
     * The LOGGER constant, which is an instance of org.slf4j.Logger used for logging events and diagnostic messages
     * during program execution.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsEventHandlerConfig.class );

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsEventHandlerConfig() {
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * This method creates and returns an instance of OSdmsEventHandler as a Spring bean. It uses the
     * getAnnotedOntimzeEventListeners() method to obtain a list of all the Ontimize Event Listeners in the application
     * context and adds them to the OSdmsEventHandler as observers.
     *
     * @return an instance of OSdmsEventHandler
     */
    @Bean
    public IOSdmsEventHandler OSdmsEventHandler() {
        final IOSdmsEventHandler result = new OSdmsEventHandler();

        final String rootPackageToFindAnnotation = this.getNameOfRootPackage();
        final List<IOSdmsEventListener> observers = this.getAnnotedOntimzeEventListeners(
                "com.ontimize.jee.sdms.event" );
        observers.addAll( this.getAnnotedOntimzeEventListeners( rootPackageToFindAnnotation ) );

        result.addEventListener( observers.toArray( new IOSdmsEventListener[ 0 ] ) );

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| UTILITIES |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * This method uses the Reflections library to find all classes in the root package that are annotated with the
     * OSdmsEventListener annotation. It instantiates each class and adds them to a list of Ontimize Event Listeners.
     *
     * @return a List of all the Ontimize Event Listeners in the application context
     *
     * @see IOSdmsEventListener
     */
    private List<IOSdmsEventListener> getAnnotedOntimzeEventListeners( final String rootPackageNameToFindAnnotation ) {
        final List<IOSdmsEventListener> result = new ArrayList<>();

        final Reflections reflections = new Reflections( rootPackageNameToFindAnnotation );
        final Set<Class<?>> listAnnotatedClasses = reflections.getTypesAnnotatedWith( OSdmsEventListener.class );

        for( final Class<?> clazz : listAnnotatedClasses ) {
            try {
                if( IOSdmsEventListener.class.isAssignableFrom( clazz ) ) {
                    final IOSdmsEventListener eventListener = ( IOSdmsEventListener ) clazz.getDeclaredConstructor().newInstance();

                    result.add( eventListener );
                }
            }
            catch( final InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException exception ) {
                LOGGER.error( exception.getMessage() );
            }
        }

        return result;
    }


    /**
     * This method returns the name of the root package where the OSdmsEventHandlerConfig class is located by getting
     * the package name from the last element of the current thread's stack trace.
     *
     * @return a String with the name of the root package
     */
    private String getNameOfRootPackage() {
        String result = "";

        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        final int lastElement = stackTraceElements.length - 1;
        final StackTraceElement lastStackTraceElement = stackTraceElements[ lastElement ];

        try {
            result = Class.forName( lastStackTraceElement.getClassName() ).getPackageName();
        }
        catch( final ClassNotFoundException exception ) {
            LOGGER.error( exception.getMessage() );
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
