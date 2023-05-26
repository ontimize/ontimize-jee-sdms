package com.ontimize.jee.sdms.rest.converter.config;

import com.ontimize.jee.sdms.rest.converter.StringJsonToOSdmsDataParamConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/**
 * Implementation of {@link WebMvcConfigurer} to add {@link StringJsonToOSdmsDataParamConverter} to the Spring MVC.
 *
 * @see WebMvcConfigurer
 * @see StringJsonToOSdmsDataParamConverter
 */
@Configuration
public class OSdmsConverterConfig implements WebMvcConfigurer {

    /** The class to convert a string to a OSdmsDataParam */
    private @Autowired StringJsonToOSdmsDataParamConverter stringJsonToOSdmsDataParamConverter;

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Method to add the {@link StringJsonToOSdmsDataParamConverter} to the Spring MVC.
     *
     * @param registry The registry of the converters.
     *
     * @see StringJsonToOSdmsDataParamConverter
     */
    @Autowired
    public void addConverters( final FormatterRegistry registry ) {
        registry.addConverter( this.stringJsonToOSdmsDataParamConverter );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
