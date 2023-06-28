package com.ontimize.jee.sdms.common.path.validator;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Implementation of the {@link IOSdmsPathValidator} interface.
 *
 * @see IOSdmsPathValidator
 */
@Component( "OSdmsPathValidator" )
public class OSdmsPathValidator implements IOSdmsPathValidator {

    private static final Pattern PATH_VARIABLE_REGEX = Pattern.compile( "\\{[\\w-:;,]+\\}" );

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean validate( final String path ) {
        return this.validate( path, Arrays.asList( "" ) );
    }

    @Override
    public boolean validate( final String path, String pattern ) {
        boolean result = true;

        if( path == null || pattern == null ) return false;

        //Initialize patterns
        final Pattern slashRegex = Pattern.compile( "\\/" );

        //Remove init stalsh if exists from pattern
        if( pattern.startsWith( "/" ) ) pattern = pattern.substring( 1 );

        //Build pattern
        final String patternRegexString = pattern
                .replaceAll( slashRegex.pattern(), "\\\\/" )
                .replaceAll( PATH_VARIABLE_REGEX.pattern(), "[\\\\w-:;,]+" );

        final Pattern patternRegex = Pattern.compile( String.format( "^\\/?(%s)+", patternRegexString ) );

        //Return result
        result = patternRegex.matcher( path ).find() &&
                ! PATH_VARIABLE_REGEX.matcher( path ).find();

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
        return PATH_VARIABLE_REGEX.matcher( path ).find();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
