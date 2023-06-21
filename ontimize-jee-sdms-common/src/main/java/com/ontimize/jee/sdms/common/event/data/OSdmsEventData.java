package com.ontimize.jee.sdms.common.event.data;

import java.util.HashMap;
import java.util.Map;


/**
 * Implementation of the {@link IOSdmsEventData} interface.
 *
 * @see IOSdmsEventData
 */
public class OSdmsEventData<T> implements IOSdmsEventData {

    /** The source */
    private Class<?> source;

    /** The triggered event */
    private Enum event;


    /** Input data */
    private Map<String, Object> inputs = new HashMap<>();

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsEventData() {
    }

    public OSdmsEventData( final Class<?> source, final Map<String, Object> inputs ) {
        this.setSource( source );
        this.setInputs( inputs );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| GETTERS AND SETTERS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Class<?> getSource() {
        return this.source;
    }

    public void setSource( final Class<?> source ) {
        this.source = source;
    }

    public Enum getEvent() {
        return this.event;
    }

    @Override
    public void setEvent( final Enum event ) {
        this.event = event;
    }

    public Map<String, Object> getInputs() {
        return this.inputs;
    }

    public void setInputs( final Map<String, Object> inputs ) {
        this.inputs = inputs;
    }


// ------------------------------------------------------------------------------------------------------------------ \\
}
