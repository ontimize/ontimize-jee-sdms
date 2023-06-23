package com.ontimize.jee.sdms.engine.s3.util.input.filter;

import java.util.List;
import java.util.Map;


/**
 * This class represents the input filter data that can be used to build a S3 engine.
 */
public class OSdmsS3InputFilter {

    /** The workspace name. */
    private String workspace;

    /* The prefix list. */
    private List<String> prefixes;

    /** The file name list. */
    private List<String> fileNames;

    /** The id list. */
    private List<String> ids;

    /** The key list. */
    private List<String> keys;

    /** The workspace data map. */
    private Map<String, Object> data;

    /** The max keys. */
    private Integer maxKeys;

    /** The marker. */
    private String marker;

    /** The delimiter. */
    private String delimiter;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3InputFilter() {
    }

    public OSdmsS3InputFilter( final String workspace, final List<String> prefixes, final List<String> fileNames, final List<String> ids, final List<String> keys, final Map<String, Object> data, final Integer maxKeys, final String marker, final String delimiter ) {
        this.setWorkspace( workspace );
        this.setPrefixes( prefixes );
        this.setFileNames( fileNames );
        this.setIds( ids );
        this.setKeys( keys );
        this.setData( data );
        this.setMaxKeys( maxKeys );
        this.setMarker( marker );
        this.setDelimiter( delimiter );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| GETTERS AND SETTERS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public String getWorkspace() {
        return this.workspace;
    }

    public void setWorkspace( final String workspace ) {
        this.workspace = workspace;
    }

    public List<String> getPrefixes() {
        return this.prefixes;
    }

    public void setPrefixes( final List<String> prefixes ) {
        this.prefixes = prefixes;
    }

    public List<String> getFileNames() {
        return this.fileNames;
    }

    public void setFileNames( final List<String> fileNames ) {
        this.fileNames = fileNames;
    }

    public List<String> getIds() {
        return this.ids;
    }

    public void setIds( final List<String> ids ) {
        this.ids = ids;
    }

    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys( final List<String> keys ) {
        this.keys = keys;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData( final Map<String, Object> data ) {
        this.data = data;
    }

    public Integer getMaxKeys() {
        return this.maxKeys;
    }

    public void setMaxKeys( final Integer maxKeys ) {
        this.maxKeys = maxKeys;
    }

    public String getMarker() {
        return this.marker;
    }

    public void setMarker( final String marker ) {
        this.marker = marker;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter( final String delimiter ) {
        this.delimiter = delimiter;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| HAS METHODS |-------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Method that checks if the input data has workspace field.
     *
     * @return true, if the input data has workspace field
     */
    public boolean hasWorkspace() {
        return this.workspace != null;
    }


    /**
     * Method that checks if the input data has prefixes field.
     *
     * @return true, if the input data has prefixes field
     */
    public boolean hasPrefixes() {
        return this.prefixes != null && ! this.prefixes.isEmpty();
    }


    /**
     * Method that checks if the input data has file names field.
     *
     * @return true, if the input data has file names field
     */
    public boolean hasFileNames() {
        return this.fileNames != null && ! this.fileNames.isEmpty();
    }


    /**
     * Method that checks if the input data has ids field.
     *
     * @return true, if the input data has ids field
     */
    public boolean hasIds() {
        return this.ids != null && ! this.ids.isEmpty();
    }


    /**
     * Method that checks if the input data has keys field.
     *
     * @return true, if the input data has keys field
     */
    public boolean hasKeys() {
        return this.keys != null && ! this.keys.isEmpty();
    }


    /**
     * Method that checks if the input data has data field.
     *
     * @return true, if the input data has data field
     */
    public boolean hasData() {
        return this.data != null && ! this.data.isEmpty();
    }


    /**
     * Method that checks if the input data has marker field.
     *
     * @return true, if the input data has marker field
     */
    public boolean hasMarker() {
        return this.marker != null;
    }


    /**
     * Method that checks if the input data has delimiter field.
     *
     * @return true, if the input data has delimiter field
     */
    public boolean hasDelimiter() {
        return this.delimiter != null;
    }


    /**
     * Method that checks if the input data has max keys field.
     *
     * @return true, if the input data has max keys field
     */
    public boolean hasMaxKeys() {
        return this.maxKeys != null;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
