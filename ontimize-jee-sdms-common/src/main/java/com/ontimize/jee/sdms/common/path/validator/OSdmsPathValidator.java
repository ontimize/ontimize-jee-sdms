package com.ontimize.jee.sdms.common.path.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Implementation of the {@link IOSdmsPathValidator} interface.
 *
 * @see IOSdmsPathValidator
 */
@Component( "OSdmsS3PathValidator" )
public class OSdmsPathValidator implements IOSdmsPathValidator {

    /**
     * The LOGGER constant, which is an instance of org.slf4j.Logger used for logging events and diagnostic messages
     * during program execution.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsPathValidator.class );

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsPathValidator() {
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean validate( final String path ) {
        return this.validate( path, Arrays.asList( "" ) );
    }

    @Override
    public boolean validate( final String path, final String pattern ) {
        boolean result = true;

        if( path == null || pattern == null ) return false;

        if( pattern != null ) {
            //Initialize patterns
            final Pattern slashRegex = Pattern.compile( "\\/" );
            final Pattern pathVariableRegex = Pattern.compile( "\\{[\\d\\w\\-_:;,.]*\\}" );

            //Build pattern
            final String patternRegexString = pattern
                    .replaceAll( slashRegex.pattern(), "\\\\/" )
                    .replaceAll( pathVariableRegex.pattern(), "[\\\\d\\\\w\\\\-_:;,.]*" );

            final Pattern patternRegex = Pattern.compile( String.format( "^(%s).*", patternRegexString ) );

            //Return result
            result = patternRegex.matcher( path ).find() &&
                    ! pathVariableRegex.matcher( path ).find();
        }

        return result;
    }

    @Override
    public boolean validate( final String path, final List<String> patterns ) {
        boolean result = false;
        for( int i = 0 ; i < patterns.size() && ! result ; i++ ) {
            final String pattern = patterns.get( i );
            result = this.validate( path, pattern );
        }
        return result;
    }

    @Override
    public boolean isPattern( final String path ) {
        final Pattern pathVariableRegex = Pattern.compile( "\\{[\\d\\w\\-_:;,.]*\\}" );
        return pathVariableRegex.matcher( path ).find();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
