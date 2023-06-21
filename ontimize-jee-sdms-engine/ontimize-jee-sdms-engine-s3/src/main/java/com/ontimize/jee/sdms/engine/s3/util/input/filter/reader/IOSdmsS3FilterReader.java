package com.ontimize.jee.sdms.engine.s3.util.input.filter.reader;

import com.ontimize.jee.sdms.engine.s3.util.input.filter.OSdmsS3InputFilter;

import java.util.List;


/**
 * Interface that defines the methods to read the filter data.
 *
 * @see OSdmsS3InputFilter
 */
public interface IOSdmsS3FilterReader {

    /**
     * Reads from filter all keys.
     *
     * @param filter The given filter.
     *
     * @return The list of keys.
     *
     * @see OSdmsS3InputFilter
     */
    List<String> readAllKeys( OSdmsS3InputFilter filter );


    /**
     * Reads from filter the key.
     *
     * @param filter The given filter.
     *
     * @return The key.
     *
     * @see OSdmsS3InputFilter
     */
    String readKey( OSdmsS3InputFilter filter );
}
