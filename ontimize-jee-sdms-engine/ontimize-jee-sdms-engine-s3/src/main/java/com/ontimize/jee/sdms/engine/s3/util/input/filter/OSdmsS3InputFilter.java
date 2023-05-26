package com.ontimize.jee.sdms.engine.s3.util.input.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;



/**
 * This class represents the input filter data that can be used to build a S3 engine.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
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
        return this.prefixes != null && !this.prefixes.isEmpty();
    }


    /**
     * Method that checks if the input data has file names field.
     *
     * @return true, if the input data has file names field
     */
    public boolean hasFileNames() {
        return this.fileNames != null && !this.fileNames.isEmpty();
    }


    /**
     * Method that checks if the input data has ids field.
     *
     * @return true, if the input data has ids field
     */
    public boolean hasIds() {
        return this.ids != null && !this.ids.isEmpty();
    }


    /**
     * Method that checks if the input data has keys field.
     *
     * @return true, if the input data has keys field
     */
    public boolean hasKeys() {
        return this.keys != null && !this.keys.isEmpty();
    }


    /**
     * Method that checks if the input data has data field.
     *
     * @return true, if the input data has data field
     */
    public boolean hasData() {
        return this.data != null && !this.data.isEmpty();
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
