package com.ontimize.jee.sdms.common.zip;

import java.util.List;



/**
 * The IOZipCompressor interface provides methods for compressing data into a ZIP archive.
 */
public interface IOSdmsZipCompressor {

    /**
     * Compresses the given data into a ZIP archive with the given name.
     *
     * @param zipName The name of the ZIP archive to be created.
     * @param data The data to be compressed into the ZIP archive.
     * @param <T> The type of data to be compressed.
     *
     * @return An {@link OSdmsZipDto} object representing the ZIP archive as a byte array and its size.
     */
    <T extends IOSdmsZippeable> OSdmsZipDto compress(String zipName, List<T> data );

}
