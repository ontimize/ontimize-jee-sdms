package com.ontimize.jee.sdms.engine.s3.util.input.filter.field;



/**
 * Enum that represents the diferent fields of the input filter.
 */
public enum OSdmsS3InputFilterField {

    WORKSPACE( "workspace" ),
    PREFIX( "prefix" ),
    FILE_NAME( "fileName" ),
    ID( "id" ),
    KEY( "key" ),
    DATA( "data" ),
    MAX_KEYS( "maxKeys" ),
    DELIMITER( "delimiter" ),
    MARKER( "marker" );

// ------------------------------------------------------------------------------------------------------------------ \\

    private String value;

// ------------------------------------------------------------------------------------------------------------------ \\

    OSdmsS3InputFilterField(final String value ){
        this.value = value;
    }


    public String getValue(){
        return this.value;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
