package com.ontimize.jee.sdms.engine.s3.repository.request;

import com.amazonaws.AmazonWebServiceRequest;

import java.io.Serializable;



/**
 * Class that represents a simple request to the S3 repository.
 *
 * @see AmazonWebServiceRequest
 * @see Serializable
 */
public class OSdmsS3RepositorySimpleRequest extends AmazonWebServiceRequest implements Serializable {

    /** The name of the S3 bucket */
    private String bucketName;

    /** The prefix of S3 */
    private String prefix;

    /** The key of S3 */
    private String key;


// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3RepositorySimpleRequest(){}

    public OSdmsS3RepositorySimpleRequest( final String bucketName, final String prefix, final String key ) {
        this.setBucketName( bucketName );
        this.setPrefix( prefix );
        this.setKey( key );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| GETTERS AND SETTERS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName( final String bucketName ) {
        this.bucketName = bucketName;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix( final String prefix ) {
        this.prefix = prefix;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey( final String key ) {
        this.key = key;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| WITH METHODS |-------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Method that sets the name of the S3 bucket.
     *
     * @param bucketName The name of the S3 bucket.
     *
     * @return The request.
     */
    public OSdmsS3RepositorySimpleRequest withBucketName(final String bucketName ){
        this.bucketName = bucketName;
        return this;
    }


    /**
     * Method that sets the prefix of S3.
     *
     * @param prefix The prefix of S3.
     *
     * @return The request.
     */
    public OSdmsS3RepositorySimpleRequest withPrefix(final String prefix ){
        this.prefix = prefix;
        return this;
    }


    /**
     * Method that sets the key of S3.
     *
     * @param key The key of S3.
     *
     * @return The request.
     */
    public OSdmsS3RepositorySimpleRequest withKey(final String key ){
        this.key = key;
        return this;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
