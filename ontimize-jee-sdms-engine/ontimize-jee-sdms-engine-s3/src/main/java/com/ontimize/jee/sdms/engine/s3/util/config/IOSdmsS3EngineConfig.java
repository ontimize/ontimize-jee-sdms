package com.ontimize.jee.sdms.engine.s3.util.config;



/**
 * Interface to define the configuration of the S3 engine.
 */
public interface IOSdmsS3EngineConfig {

    /**
     * Sets the bucket name.
     *
     * @param bucket the bucket name.
     */
    void setBucket( String bucket);


    /**
     * Gets the bucket name.
     *
     * @return the bucket name.
     */
    String getBucket();

}
