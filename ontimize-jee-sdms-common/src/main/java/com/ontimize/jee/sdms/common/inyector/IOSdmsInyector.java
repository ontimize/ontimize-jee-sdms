package com.ontimize.jee.sdms.common.inyector;


/**
 oSdmsZnterface that defines the methods obtain dependencies from the context.
 */
public interface IOSdmsInyector {

    /**
     * Gets the dependency from the context from its name.
     *
     * @param name The name of the dependency.
     *
     * @return The dependency.
     */
    Object get( String name );



    /**
     * Gets the dependency from the context from its class.
     *
     * @param clazz The class of the dependency.
     *
     * @return The dependency.
     */
    <T> T get( Class<T> clazz );



    /**
     * Gets the dependency from the context from its name and class.
     *
     * @param name The name of the dependency.
     * @param clazz The class of the dependency.
     *
     * @return The dependency.
     */
    <T> T get( String name, Class<T> clazz );

}
