package com.ontimize.jee.sdms.engine.s3.util.input.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;



/**
 * This class represents the input data that can be used to build a S3 engine.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OSdmsS3InputData {

    /** The current prefix from the realized the action. */
    private String currentPrefix;

    /** The prefix. */
    private String prefix;

    /** The file name. */
    private String fileName;

    /** The id. */
    private String id;

    /** The key. */
    private String key;

    /** The metadata. */
    private Map<String, String> metadata;

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Method that checks if the input data has current prefix field.
     *
     * @return true, if the input data has current prefix field
     */
    public boolean hasCurrentPrefix() {
        return this.currentPrefix != null;
    }


    /**
     * Method that checks if the input data has prefix field.
     *
     * @return true, if the input data has prefix field
     */
    public boolean hasPrefix() {
        return this.prefix != null;
    }


    /**
     * Method that checks if the input data has file name field.
     *
     * @return true, if the input data has file name field
     */
    public boolean hasFileName() {
        return this.fileName != null;
    }


    /**
     * Method that checks if the input data has id field.
     *
     * @return true, if the input data has id field
     */
    public boolean hasId() {
        return this.id != null;
    }


    /**
     * Method that checks if the input data has key field.
     *
     * @return true, if the input data has key field
     */
    public boolean hasKey() {
        return this.key != null;
    }


    /**
     * Method that checks if the input data has metadata field.
     *
     * @return true, if the input data has metadata field
     */
    public boolean hasMetadata() {
        return this.metadata != null && !this.metadata.isEmpty();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
