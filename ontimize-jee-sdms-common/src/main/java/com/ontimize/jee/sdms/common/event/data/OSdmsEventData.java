package com.ontimize.jee.sdms.common.event.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


/**
 * Implementation of the {@link IOSdmsEventData} interface.
 *
 * @see IOSdmsEventData
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OSdmsEventData<T> implements IOSdmsEventData {

    /** The source */
    private Class<?> source;

    /** The triggered event */
    private Enum event;


    /** Input data */
    private Map<String, Object> inputs = new HashMap<>();

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsEventData(final Class<?> source, final Map<String, Object> inputs ){
        this.setSource( source );
        this.setInputs( inputs );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void setEvent( final Enum event ){
        this.event = event;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
}
