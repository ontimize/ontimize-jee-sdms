package com.ontimize.jee.sdms.engine.s3.repository.response;

import com.ontimize.jee.sdms.engine.s3.repository.response.codes.OSdmsS3RepositoryResponseCodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Class that represents the response of the S3 Repository.
 */
public class OSdmsS3RepositoryResponse<T> {

    /** The response code of the S3 Repository response */
    private OSdmsS3RepositoryResponseCodes code = OSdmsS3RepositoryResponseCodes.OK;

    /** The response message of the S3 Repository response */
    private String message;

    /** The data of the S3 Repository response */
    private List<T> data = new ArrayList<>();

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3RepositoryResponse() {
    }

    /**
     * Constructor of the class.
     *
     * @param code The response code of the S3 Repository response.
     */
    public OSdmsS3RepositoryResponse( final OSdmsS3RepositoryResponseCodes code ) {
        this.setCode( code );
    }

    /**
     * Constructor of the class.
     *
     * @param code    The response code of the S3 Repository response.
     * @param message The response message of the S3 Repository response.
     */
    public OSdmsS3RepositoryResponse( final OSdmsS3RepositoryResponseCodes code, final String message ) {
        this.setCode( code );
        this.setMessage( message );
    }

    public OSdmsS3RepositoryResponse( final OSdmsS3RepositoryResponseCodes code, final String message, final List<T> data ) {
        this.setCode( code );
        this.setMessage( message );
        this.setData( data );
    }

    // ------------------------------------------------------------------------------------------------------------------ \\
// ------| GETTERS AND SETTERS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3RepositoryResponseCodes getCode() {
        return this.code;
    }

    public void setCode( final OSdmsS3RepositoryResponseCodes code ) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage( final String message ) {
        this.message = message;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData( final List<T> data ) {
        this.data = data;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| ADD METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Method that adds data to the response.
     *
     * @param data The data to add.
     */
    public void add( final Collection<T> data ) {
        this.data.addAll( data );
    }


    /**
     * Method that adds data to the response.
     *
     * @param data The data to add.
     */
    public void add( final T data ) {
        this.data.add( data );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
