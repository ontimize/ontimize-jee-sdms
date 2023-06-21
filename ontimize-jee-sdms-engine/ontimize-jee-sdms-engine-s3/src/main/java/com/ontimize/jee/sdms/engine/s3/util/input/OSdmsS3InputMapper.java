package com.ontimize.jee.sdms.engine.s3.util.input;

import com.ontimize.jee.sdms.common.dto.OSdmsRestDataDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Abstract class to define methods to map the data to be sent to the DMS.
 *
 * @param <T> The type of data to be mapped.
 */
public abstract class OSdmsS3InputMapper<T> {

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| ABSTRACT METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Maps the given {@link OSdmsRestDataDto} object to the given type.
     *
     * @param data The {@link OSdmsRestDataDto} object to be mapped.
     *
     * @return The mapped object.
     */
    public abstract T map( OSdmsRestDataDto data );

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| UTILITES |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Gets the value as Map of the given field from the given data.
     *
     * @param field The name of the field to be retrieved.
     * @param data  The data from which the field is to be retrieved.
     *
     * @return The value of the given field from the given data as Map.
     */
    protected Map<String, Object> getMapStringObject( final String field, final Map<String, Object> data ) {
        Map<String, Object> result = null;//
        if( data.containsKey( field ) ) {
            final Object value = data.get( field );

            if( value instanceof Map ) {
                result = ( Map<String, Object> ) value;
            }
        }
        return result;
    }


    /**
     * Gets the value as List of the given field from the given data.
     *
     * @param field The name of the field to be retrieved.
     * @param data  The data from which the field is to be retrieved.
     *
     * @return The value of the given field from the given data as List.
     */
    protected List<String> getListString( final String field, final Map<String, Object> data ) {
        List<String> result = null;//
        if( data.containsKey( field ) ) {
            final Object value = data.get( field );
            result = new ArrayList<>( Arrays.asList( String.valueOf( value ) ) );

            if( value instanceof List ) {
                result = ( List<String> ) value;
            }
        }
        return result;
    }


    /**
     * Gets the value as String of the given field from the given data.
     *
     * @param field The name of the field to be retrieved.
     * @param data  The data from which the field is to be retrieved.
     *
     * @return The value of the given field from the given data as String.
     */
    protected String getString( final String field, final Map<String, Object> data ) {
        String result = null;//
        if( data.containsKey( field ) ) {
            final Object value = data.get( field );
            result = String.valueOf( value );
        }
        return result;
    }


    /**
     * Gets the value as Integer of the given field from the given data.
     *
     * @param field The name of the field to be retrieved.
     * @param data  The data from which the field is to be retrieved.
     *
     * @return The value of the given field from the given data as Integer.
     */
    protected Integer getInteger( final String field, final Map<String, Object> data ) {
        Integer result = null;//
        if( data.containsKey( field ) ) {
            final Object value = data.get( field );
            result = Integer.valueOf( String.valueOf( value ) );
        }
        return result;
    }
}
