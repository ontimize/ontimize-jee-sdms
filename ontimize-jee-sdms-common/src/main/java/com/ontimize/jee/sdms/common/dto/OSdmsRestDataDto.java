package com.ontimize.jee.sdms.common.dto;

import java.util.Map;


/**
 * Class to define the data to be sent to the DMS.
 */
public class OSdmsRestDataDto {

    /** The map containing the filter data. */
    private Map<String, Object> filter;

    /** The map containing the data to be sent. */
    private Map<String, Object> data;

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| GETTERS AND SETTERS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Map<String, Object> getFilter() {
        return this.filter;
    }

    public void setFilter( final Map<String, Object> filter ) {
        this.filter = filter;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData( final Map<String, Object> data ) {
        this.data = data;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| TO STRING |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public String toString() {
        return new StringBuilder( "OSdmsRestDataDto{" )
                .append( "filter=" ).append( this.getData() )
                .append( ", data=" ).append( this.getFilter() )
                .append( '}' )
                .toString();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
