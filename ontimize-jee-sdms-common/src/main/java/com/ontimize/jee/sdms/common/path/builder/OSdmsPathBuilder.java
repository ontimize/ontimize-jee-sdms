package com.ontimize.jee.sdms.common.path.builder;

import com.ontimize.jee.sdms.common.path.validator.IOSdmsPathValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Implementation of the {@link IOSdmsPathBuilder} interface.
 *
 * @see IOSdmsPathBuilder
 */
@Component( "OSdmsPathBuilder" )
public class OSdmsPathBuilder implements IOSdmsPathBuilder {

    /** The pattern to check a valid characters to replace variables in the pattern by values. */
    private static final Pattern VALID_CHARACTERS_TO_REPLACE = Pattern.compile( "[\\/:\\*\\?\\\"<>|\\{\\}\\[\\]\\.]" );

    /** The path validator. */
    private @Autowired IOSdmsPathValidator pathValidator;

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public List<String> buildKeyListFromPattern( final String pattern, final Map<String, Object> data ) {
        List<String> result = new ArrayList<>();
        result.addAll( List.of( pattern ) );

        if( pattern != null && data != null ) {
            for( final Map.Entry<String, Object> entry : data.entrySet() ) {
                //Get Data
                final String key = entry.getKey();
                final Object value = entry.getValue();

                List<Object> values = Arrays.asList( value );

                //Check if the value is a List
                if( value instanceof List ) {
                    values = ( ( List ) value );
                }

                result = this.buildKeyListFromPattern( result, key, values );
            }
        }

        return result.stream().collect( Collectors.toList() );
    }


    @Override
    public List<String> buildKeyListFromPattern( final List<String> patterns, String key, final List<Object> values ) {
        final Set<String> result = new HashSet<>();

        patterns.forEach( pattern ->
                                  values.forEach( value -> {
                                      final String valueToReplace = String.valueOf( value );
                                      if( ! VALID_CHARACTERS_TO_REPLACE.matcher( valueToReplace ).find() ) {
                                          final String regex = String.format( "\\{%s\\}", key );
                                          final String newPattern = pattern.replaceAll( regex, valueToReplace );
                                          result.add( newPattern );
                                      }
                                  } )
                        );

        return new ArrayList<>( result );
    }


    @Override
    public String buildKey( final String workspace, final String prefix, final String fileName ) {
        String result = null;

        if( workspace != null ) result = this.addValueToPath( result, workspace );
        if( prefix != null ) result = this.addValueToPath( result, prefix );
        if( fileName != null ) result = this.addValueToPath( result, fileName );

        return result;
    }


    @Override
    public List<String> buildKeyList( final List<String> workspaces, final String prefix, final String fileName ) {
        final List<String> prefixes = prefix != null ? List.of( prefix ) : Collections.emptyList();
        final List<String> fileNames = fileName != null ? List.of( fileName ) : Collections.emptyList();
        return this.buildKeyList( workspaces, prefixes, fileNames );
    }


    @Override
    public List<String> buildKeyList( final List<String> workspaces, final List<String> prefixes, final List<String> fileNames ) {
        Set<String> result = new HashSet<>();

        if( ! prefixes.isEmpty() || ! fileNames.isEmpty() ) {
            final Set<String> newPaths = new HashSet<>();

            //Workspaces
            result.addAll( this.addValuesToPath( null, workspaces ) );

            //Prefixes
            if( result.isEmpty() ) {
                newPaths.addAll( this.addValuesToPath( null, prefixes ) );
            }
            else {
                result.forEach( target -> newPaths.addAll( this.addValuesToPath( target, prefixes ) ) );
            }

            //Save new paths
            if( ! newPaths.isEmpty() ) {
                result = newPaths.stream()
                        .map( target -> ! target.endsWith( "/" ) ? target.concat( "/" ) : target )
                        .collect( Collectors.toSet() );
                newPaths.clear();
            }

            //Names
            if( result.isEmpty() ) {
                newPaths.addAll( this.addValuesToPath( null, fileNames ) );
            }
            else {
                result.forEach( target -> newPaths.addAll( this.addValuesToPath( target, fileNames ) ) );
            }

            //Save new paths
            if( ! newPaths.isEmpty() ) {
                result = new HashSet<>( newPaths );
                newPaths.clear();
            }
        }

        return new ArrayList<>( result );
    }


    @Override
    public List<String> buildKeyList( final List<String> prefixes, final List<String> fileNames ) {
        Set<String> result = new HashSet<>();

        if( ! prefixes.isEmpty() || ! fileNames.isEmpty() ) {
            final Set<String> newPaths = new HashSet<>();

            //Prefixes
            result.addAll( this.addValuesToPath( null, prefixes ) );

            //Names
            if( result.isEmpty() ) {
                result.addAll( this.addValuesToPath( null, fileNames ) );
            }
            else {
                result.forEach( target -> newPaths.addAll( this.addValuesToPath( target, fileNames ) ) );
                result = new HashSet<>( newPaths );
                newPaths.clear();
            }
        }

        return new ArrayList<>( result );
    }


    @Override
    public String buildKey( final String prefix, final String fileName ) {
        String result = null;

        if( prefix != null ) result = this.addValueToPath( result, prefix );
        if( fileName != null ) result = this.addValueToPath( result, fileName );

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| UTILITIES |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Add a value String to a path.
     *
     * @param path  The path String
     * @param value The value String
     *
     * @return Path with the value added
     */
    private String addValueToPath( final String path, final String value ) {
        final String initValue = path != null ? path : "";
        final StringBuilder result = new StringBuilder( initValue );

        if( value != null ) {
            if( result.length() > 0 && ! result.toString().endsWith( "/" ) && ! value.startsWith( "/" ) ) {
                result.append( "/" );
            }
            result.append( value );
        }

        if( result.toString().endsWith( "/" ) ) result.delete( result.length() - 1, result.length() );

        return result.toString();
    }


    /**
     * Add a values String to a path.
     *
     * @param path   The path String
     * @param values The values String
     *
     * @return Path list with the values added
     */
    private List<String> addValuesToPath( final String path, final List<String> values ) {
        final Set<String> result = new HashSet<>();

        if( values != null ) {
            values.forEach( value -> result.add( this.addValueToPath( path, value ) ) );
        }

        return new ArrayList<>( result );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
