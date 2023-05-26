package com.ontimize.jee.sdms.common.dto;

import lombok.Data;

import java.util.Map;



/**
 * Class to define the data to be sent to the DMS.
 */
@Data
public class OSdmsRestDataDto {

    /** The map containing the filter data. */
    private Map<String, Object> filter;

    /** The map containing the data to be sent. */
    private Map<String, Object> data;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsRestDataDto() {}

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| GETTERS AND SETTERS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Map<String, Object> getFilter() {
        return this.filter;
    }

    public void setFilter( final Map<String, Object> filter) {
        this.filter = filter;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(final Map<String, Object> data) {
        this.data = data;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
