package com.ontimize.jee.sdms.engine.s3.util.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Implementation of {@link IOSdmsS3EngineConfig} interface.
 *
 * @see IOSdmsS3EngineConfig
 */
@Component( "OSdmsS3EngineConfig" )
public class OSdmsS3EngineConfig implements IOSdmsS3EngineConfig {

    /** The bucket name from configuration in the Applitation.yaml. */
    @Value( "${ontimize.sdms.s3.bucket}" )
    private String bucket;

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public String getBucket() {
        return this.bucket;
    }

    @Override
    public void setBucket( final String bucket ) {
        this.bucket = bucket;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
