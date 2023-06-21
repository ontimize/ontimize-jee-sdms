package com.ontimize.jee.sdms.engine.s3.util.input.data.field;


/**
 * Enum that represents the diferent fields of the input data.
 */
public enum OSdmsS3InputDataField {

    CURRENT_PREFIX( "currentPrefix" ),
    PREFIX( "prefix" ),
    FILE_NAME( "fileName" ),
    ID( "id" ),
    KEY( "key" ),
    METADATA( "metadata" );

// ------------------------------------------------------------------------------------------------------------------ \\

    private String value;

// ------------------------------------------------------------------------------------------------------------------ \\

    OSdmsS3InputDataField( final String value ) {
        this.value = value;
    }


    public String getValue() {
        return this.value;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
