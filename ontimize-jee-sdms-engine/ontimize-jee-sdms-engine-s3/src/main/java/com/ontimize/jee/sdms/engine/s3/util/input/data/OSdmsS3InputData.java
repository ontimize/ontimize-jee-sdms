package com.ontimize.jee.sdms.engine.s3.util.input.data;

import java.util.Map;



/**
 * This class represents the input data that can be used to build a S3 engine.
 */
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

    public OSdmsS3InputData(){}

    public OSdmsS3InputData( final String currentPrefix, final String prefix, final String fileName, final String id, final String key, final Map<String, String> metadata) {
        this.setCurrentPrefix( currentPrefix );
        this.setPrefix( prefix );
        this.setFileName( fileName );
        this.setId( id );
        this.setKey( key );
        this.setMetadata( metadata );
    }

    // ------------------------------------------------------------------------------------------------------------------ \\
// -------| GETTERS AND SETTERS |------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public String getCurrentPrefix() {
        return this.currentPrefix;
    }

    public void setCurrentPrefix( final String currentPrefix ) {
        this.currentPrefix = currentPrefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix( final String prefix ) {
        this.prefix = prefix;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName( final String fileName ) {
        this.fileName = fileName;
    }

    public String getId() {
        return this.id;
    }

    public void setId( final String id ) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey( final String key ) {
        this.key = key;
    }

    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    public void setMetadata( final Map<String, String> metadata ) {
        this.metadata = metadata;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| HAS METHODS |-------------------------------------------------------------------------------------------- \\
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
