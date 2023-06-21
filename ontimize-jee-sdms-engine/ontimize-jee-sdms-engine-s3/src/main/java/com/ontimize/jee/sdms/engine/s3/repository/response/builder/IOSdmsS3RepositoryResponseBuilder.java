package com.ontimize.jee.sdms.engine.s3.repository.response.builder;

import com.ontimize.jee.sdms.engine.s3.repository.response.OSdmsS3RepositoryResponse;
import com.ontimize.jee.sdms.engine.s3.repository.response.codes.OSdmsS3RepositoryResponseCodes;

import java.util.List;
import java.util.Set;


/**
 * Interface that defines the methods to build the S3 repository response.
 */
public interface IOSdmsS3RepositoryResponseBuilder {

    /**
     * Method that sets the response code.
     *
     * @param code The code to set.
     *
     * @return The builder.
     *
     * @see OSdmsS3RepositoryResponseCodes
     */
    IOSdmsS3RepositoryResponseBuilder code( OSdmsS3RepositoryResponseCodes code );


    /**
     * Method that sets the response message.
     *
     * @param message The message to set.
     *
     * @return The builder.
     */
    IOSdmsS3RepositoryResponseBuilder message( String message );


    /**
     * Method that builds the response without data.
     *
     * @return The S3 repository response.
     */
    OSdmsS3RepositoryResponse build();


    /**
     * Method that builds the response with data.
     *
     * @param data The data to set.
     *
     * @return The S3 repository response.
     */
    <T> OSdmsS3RepositoryResponse<T> build( T data );


    /**
     * Method that builds the response with data list.
     *
     * @param data The data list to set.
     *
     * @return The S3 repository response.
     */
    <T> OSdmsS3RepositoryResponse<T> build( List<T> data );


    /**
     * Method that builds the response with data set.
     *
     * @param data The data set to set.
     *
     * @return The S3 repository response.
     */
    <T> OSdmsS3RepositoryResponse<T> build( Set<T> data );

}
