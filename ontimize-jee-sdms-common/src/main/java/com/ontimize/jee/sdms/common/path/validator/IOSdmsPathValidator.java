package com.ontimize.jee.sdms.common.path.validator;


import java.util.List;


/**
 * Interface that defines the methods to validate a path.
 *
 * @see IOSdmsPathValidator
 */
public interface IOSdmsPathValidator {

    /**
     * Validates the given path.
     *
     * @param path The given path to validate.
     *
     * @return True if the given path is valid, false otherwise.
     */
    boolean validate( String path );


    /**
     * Validates the given path with the given pattern.
     *
     * @param path    The given path to validate.
     * @param pattern The given pattern to validate.
     *
     * @return True if the given path is valid, false otherwise.
     */
    boolean validate( String path, String pattern );


    /**
     * Validates the given path with the given patterns.
     *
     * @param path     The given path to validate.
     * @param patterns The given patterns to validate.
     *
     * @return True if the given path is valid, false otherwise.
     */
    boolean validate( String path, List<String> patterns );


    /**
     * Checks if the given path is a pattern.
     *
     * @param path The given path to check.
     *
     * @return True if the given path is a pattern, false otherwise.
     */
    boolean isPattern( String path );

}
