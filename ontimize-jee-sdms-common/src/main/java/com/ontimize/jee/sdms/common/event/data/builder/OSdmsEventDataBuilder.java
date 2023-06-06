package com.ontimize.jee.sdms.common.event.data.builder;

import com.ontimize.jee.sdms.common.event.data.OSdmsEventData;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;




/**
 * Implementation of the {@link IOSdmsEventDataBuilder} interface.
 *
 * @see IOSdmsEventDataBuilder
 */
@Component( "OSdmsEventDataBuilder" )
public class OSdmsEventDataBuilder implements IOSdmsEventDataBuilder {

    /** Source */
    private Class<?> source;

    /** Input data */
    private Map<String, Object> inputs = new HashMap<>();

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsEventDataBuilder(){}

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsEventDataBuilder source(final Class<?> source ){
        this.source = source;
        return this;
    }

    @Override
    public OSdmsEventDataBuilder input(final Enum key, final Object value ){
        this.inputs.put( key.name(), value );
        return this;
    }

    @Override
    public OSdmsEventDataBuilder input(final String key, final Object value ){
        this.inputs.put( key, value );
        return this;
    }

    @Override
    public OSdmsEventData build() {
        final OSdmsEventData result = new OSdmsEventData( this.source, this.inputs );
        this.clear();
        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| GETTERS |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Class<?> getSource() {
        return this.source;
    }

    public Map<String, Object> getInputs() {
        return this.inputs;
    }


// ------------------------------------------------------------------------------------------------------------------ \\
// -------| UTILITIES |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Clears the builder information.
     */
    private void clear(){
        this.source = null;
        this.inputs.clear();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
