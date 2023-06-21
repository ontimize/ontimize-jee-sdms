package com.ontimize.jee.sdms.rest.converter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontimize.jee.sdms.common.dto.OSdmsRestDataDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * Implementation of {@link Converter} to convert a {@link String} to a {@link OSdmsRestDataDto}.
 *
 * @see Converter
 * @see OSdmsRestDataDto
 */
@Component( "StringJsonToOSdmsDataParamConverter" )
public class StringJsonToOSdmsDataParamConverter implements Converter<String, OSdmsRestDataDto> {

// ------------------------------------------------------------------------------------------------------------------ \\

    public StringJsonToOSdmsDataParamConverter() {
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsRestDataDto convert( final String source ) {
        //Intialize the result and ObjectMapper
        OSdmsRestDataDto result = null;
        final ObjectMapper mapper = new ObjectMapper();

        try {
            //Convert source string to JSON
            final JsonNode jsonNode = mapper.readTree( source );

            //Convert JSON to Map
            final Map<String, Object> data = mapper.convertValue( jsonNode, HashMap.class );

            //Set data in the result
            result = new OSdmsRestDataDto();
            result.setFilter( ( Map<String, Object> ) data.get( "filter" ) );
            result.setData( ( Map<String, Object> ) data.get( "data" ) );
        }
        catch( final JsonProcessingException e ) {
            //Empty
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
