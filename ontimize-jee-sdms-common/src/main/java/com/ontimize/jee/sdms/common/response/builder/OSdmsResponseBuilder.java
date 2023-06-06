package com.ontimize.jee.sdms.common.response.builder;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



/**
 * Implementation of the interface {@link IOSdmsResponseBuilder}.
 *
 * @see IOSdmsResponseBuilder
 */
@Component( "OSdmsResponseBuilder")
public class OSdmsResponseBuilder implements IOSdmsResponseBuilder {

    /** The code of the response */
    private Integer code;

    /** The message of the response */
    private String message;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsResponseBuilder(){}

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public IOSdmsResponseBuilder code(final Integer code ) {
        this.code = code;
        return this;
    }

    @Override
    public IOSdmsResponseBuilder message(final String message ) {
        this.message = message;
        return this;
    }

    @Override
    public EntityResult build() {
        return this.build( this.code, this.message, null );
    }

    @Override
    public EntityResult buildWithMap( final Map data ) {
        return this.build( this.code, this.message, Arrays.asList( data ));
    }

    @Override
    public EntityResult buildWithMap( final List<Map> data ) {
        return this.build( this.code, this.message, data );
    }

    @Override
    public <T extends IOSdmsMappeable> EntityResult buildWithMappeable(final T data ) {
        final List<Map> dataMap = Arrays.asList( data.toMap() );
        return this.build( this.code, this.message, dataMap );
    }

    @Override
    public <T extends IOSdmsMappeable> EntityResult buildWithMappeable(final List<T> data ) {
        final List<Map> dataMap = data.stream().map( IOSdmsMappeable::toMap ).collect( Collectors.toList() );
        return this.build( this.code, this.message, dataMap );
    }
// ------------------------------------------------------------------------------------------------------------------ \\
// ------| UTILITIES |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Clears the information of the builder.
     */
    private void clear(){
        this.code = null;
        this.message = null;
    }



    /**
     * Builds the response.
     *
     * @param code The code of the response
     * @param message The message of the response
     * @param data The data to build the response
     *
     * @return The response as an EntityResult
     */
    private EntityResult build( final Integer code, final String message, final List<Map> data ){
        final EntityResult result = new EntityResultMapImpl();
        if( this.code != null ) result.setCode( Integer.valueOf( code ));
        if( this.message != null ) result.setMessage( message );
        if( data != null ) data.forEach( target -> result.addRecord( target ));
        this.clear();
        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
}
