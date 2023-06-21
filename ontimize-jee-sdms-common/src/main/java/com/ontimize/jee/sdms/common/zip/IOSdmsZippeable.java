package com.ontimize.jee.sdms.common.zip;


/**
 * The IOSdmsZippeable interface defines the behavior that an object must implement to be able to be compressed using
 * the {@link IOSdmsZipCompressor} implementation.
 */
public interface IOSdmsZippeable {

    /**
     * This method returns an {@link OSdmsZipData} instance containing the data that will be compressed
     *
     * @return an {@link OSdmsZipData} instance containing the data that will be compressed
     */
    OSdmsZipData getDataToZip();

}
