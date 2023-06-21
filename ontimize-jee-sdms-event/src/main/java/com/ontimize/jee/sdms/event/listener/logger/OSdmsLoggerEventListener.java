package com.ontimize.jee.sdms.event.listener.logger;

import com.ontimize.jee.sdms.common.event.data.OSdmsEventData;
import com.ontimize.jee.sdms.common.event.listener.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.OSdmsS3RepositoryEvent;
import com.ontimize.jee.sdms.event.OSdmsServiceEvent;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * This class represents a S3 Repository Event Listener that logs the events of the S3 repository.
 *
 * @see IOSdmsEventListener
 * @see OSdmsEventData
 */
@OSdmsEventListener
public class OSdmsLoggerEventListener implements IOSdmsEventListener<OSdmsEventData> {

    /**
     * The LOGGER constant, which is an instance of org.slf4j.Logger used for logging events and diagnostic messages
     * during program execution.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsLoggerEventListener.class );


    /**
     * The events constant, which is a List of {@link OSdmsS3RepositoryEvent} that represents the events that this
     * listener will listen.
     */
    private final List<Enum> events = new ArrayList<>();

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsLoggerEventListener() {
        this.events.addAll( Arrays.asList( OSdmsServiceEvent.values() ) );
        this.events.addAll( Arrays.asList( OSdmsS3RepositoryEvent.values() ) );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public List<Enum> getEvents() {
        return this.events;
    }


    @Override
    public void run( final OSdmsEventData data ) {
        if( LOGGER.isDebugEnabled() ) {
            String inputData = "[]";

            try {
                inputData = this.getDataAsStringFromListObject( data.getInputs() );
            }
            catch( final IllegalAccessException e ) {
                //Empty catch block
            }

            //Initialize the init message of String Builder with event name
            final String msg = String.format( "Event Listener Data: { Source: \"%s\", Event: \"%s\", Input: %s }",
                                              data.getSource().getCanonicalName(), data.getEvent(), inputData
                                            );

            LOGGER.debug( "{}", msg );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| UTILITIES |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * This method returns a String representation of the data object.
     *
     * @param data The data object to be represented as String.
     *
     * @return A String representation of the data object.
     *
     * @throws IllegalAccessException If the data object cannot be accessed.
     */
    private <T> String getDataAsStringFromObject( final T data ) throws IllegalAccessException {
        //Initialize
        String result = "[]";
        final List<String> objects = new ArrayList<>();

        //Check data
        if( data == null ) return "";

        // Get list of data
        List<Object> list = Arrays.asList( data );
        if( data instanceof List ) list = ( List ) data;

        //Iterate list
        for( final Object target : list ) {
            //Initialize builder
            final StringBuilder resultBuilder = new StringBuilder();

            //Get fields from target object
            final Field[] fields = target.getClass().getDeclaredFields();

            //Iterate fields
            for( final Field field : fields ) {
                //Get data from field
                field.setAccessible( true );
                final String name = field.getName();
                final Object value = field.get( target );

                //Check field value
                if( value != null ) { //If value is not null, add it to builder
                    resultBuilder.append( String.format( "%s: %s, ", name, value ) );
                }
            }

            //Check builder length
            if( resultBuilder.length() > 0 ) { //If builder isn't empty, add it to objects list string
                resultBuilder.delete( resultBuilder.length() - 2, resultBuilder.length() );
                objects.add( String.format( "{ %s }", resultBuilder ) );
            }
        }

        //Check objects list
        if( objects.size() > 1 ) { //If isn't empty, add it to result with brackets
            String objectsString = String.valueOf( objects );
            objectsString = objectsString.substring( 1, objectsString.length() - 1 );
            result = String.format( "[ %s ]", objectsString );
        }
        else if( objects.size() == 1 ) { //If only has one element, add it to result without brackets
            result = String.format( "%s", objects.get( 0 ) );
        }

        return result;
    }


    /**
     * This method returns a String representation of the Map data object.
     *
     * @param data The data object to be represented as String.
     *
     * @return A String representation of the data object.
     *
     * @throws IllegalAccessException If the data object cannot be accessed.
     */
    private String getDataAsStringFromListObject( final Map<String, Object> data ) throws IllegalAccessException {
        //Initialize result
        String result = "[]";
        final StringBuilder resultBuilder = new StringBuilder();

        //Check data
        if( data == null ) return result;

        //Iterate data
        for( final Map.Entry<String, Object> entry : data.entrySet() ) {
            //Get data
            final String key = entry.getKey();
            final Object value = entry.getValue();
            String dataString = String.valueOf( value );
            if( ! ( value instanceof String ) ) dataString = this.getDataAsStringFromObject( value );

            //Add data to builder
            resultBuilder.append( String.format( "{ %s: %s }, ", key, dataString ) );
        }

        //Check builder length
        if( resultBuilder.length() > 0 ) { //If builder isn't empty, add it to result with brackets
            resultBuilder.delete( resultBuilder.length() - 2, resultBuilder.length() );
            result = String.format( "[ %s ]", resultBuilder );
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}