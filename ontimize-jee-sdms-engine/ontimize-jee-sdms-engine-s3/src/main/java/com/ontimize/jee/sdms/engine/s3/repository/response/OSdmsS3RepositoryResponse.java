package com.ontimize.jee.sdms.engine.s3.repository.response;

import com.ontimize.jee.sdms.engine.s3.repository.response.codes.OSdmsS3RepositoryResponseCodes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



/**
 * Class that represents the response of the S3 Repository.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OSdmsS3RepositoryResponse<T> {

    /** The response code of the S3 Repository response */
    private OSdmsS3RepositoryResponseCodes code = OSdmsS3RepositoryResponseCodes.OK;

    /** The response message of the S3 Repository response */
    private String message;

    /** The data of the S3 Repository response */
    private List<T> data = new ArrayList<>();

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor of the class.
     *
     * @param code The response code of the S3 Repository response.
     */
    public OSdmsS3RepositoryResponse(final OSdmsS3RepositoryResponseCodes code ) {
        this.code = code;
    }

    /**
     * Constructor of the class.
     *
     * @param code The response code of the S3 Repository response.
     * @param message The response message of the S3 Repository response.
     */
    public OSdmsS3RepositoryResponse(final OSdmsS3RepositoryResponseCodes code, final String message ) {
        this.code = code;
        this.message = message;
    }

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
