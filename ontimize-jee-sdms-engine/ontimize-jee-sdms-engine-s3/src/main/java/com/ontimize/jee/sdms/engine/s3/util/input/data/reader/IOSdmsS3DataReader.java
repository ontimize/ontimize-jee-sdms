package com.ontimize.jee.sdms.engine.s3.util.input.data.reader;


import com.ontimize.jee.sdms.engine.s3.util.input.data.OSdmsS3InputData;

/**
 * Interface that defines the methods to read the data.
 *
 * @see OSdmsS3InputData
 */
public interface IOSdmsS3DataReader {

    /**
     * Reads the key from data
     *
     * @param data The given data.
     *
     * @return The key
     *
     * @see OSdmsS3InputData
     */
    String readKey( OSdmsS3InputData data );


    /**
     * Reads the prefix from data
     *
     * @param data The given data.
     *
     * @return The prefix
     *
     * @see OSdmsS3InputData
     */
    String readPrefix( OSdmsS3InputData data );


    /**
     * Reads the current prefix from data
     *
     * @param data The given data.
     *
     * @return The current prefix
     *
     * @see OSdmsS3InputData
     */
    String readCurrentPrefix( OSdmsS3InputData data );
}
