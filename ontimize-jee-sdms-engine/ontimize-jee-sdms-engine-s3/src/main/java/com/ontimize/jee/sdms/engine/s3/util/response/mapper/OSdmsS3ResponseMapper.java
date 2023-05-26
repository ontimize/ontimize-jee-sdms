package com.ontimize.jee.sdms.engine.s3.util.response.mapper;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.sdms.common.response.builder.IOSdmsMappeable;
import com.ontimize.jee.sdms.common.response.builder.IOSdmsResponseBuilder;
import com.ontimize.jee.sdms.engine.s3.repository.response.OSdmsS3RepositoryResponse;
import com.ontimize.jee.sdms.engine.s3.repository.response.codes.OSdmsS3RepositoryResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Implementation of {@link IOSdmsS3ResponseMapper} interface.
 *
 * @see IOSdmsS3ResponseMapper
 */
@Component( "OSdmsS3ResponseMapper" )
public class OSdmsS3ResponseMapper implements IOSdmsS3ResponseMapper {

    private static final String MESSAGE_ERROR = "An error has occurred and the operation could not be performed.";

    /** The Ontimize response builder */
    private @Autowired IOSdmsResponseBuilder responseBuilder;

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public <T extends IOSdmsMappeable> EntityResult map(final OSdmsS3RepositoryResponse<T> response ) {
        //Intialize Response
        EntityResult result = this.responseBuilder
                .code( EntityResult.OPERATION_WRONG )
                .message( MESSAGE_ERROR )
                .build();

        //Check Response
        if( response != null ) {
            result = this.responseBuilder
                    .code( response.getCode().getEntityResultCode() )
                    .message( response.getMessage() )
                    .build();

            final List<T> data = response.getData();
            if( response.getCode() == OSdmsS3RepositoryResponseCodes.OK && data != null && !data.isEmpty() ) {
                for( final T mappeable : data ) {
                    result.addRecord( mappeable.toMap() );
                }
            }
        }

        return result;
    }


    @Override
    public <T extends IOSdmsMappeable> EntityResult map(final T data ) {
        //Intialize Response
        EntityResult result = this.responseBuilder
                .code( EntityResult.OPERATION_WRONG )
                .message( MESSAGE_ERROR )
                .build();

        //Check Response
        if( data != null ) {
            result = this.responseBuilder
                    .code( EntityResult.OPERATION_SUCCESSFUL )
                    .buildWithMappeable( data );
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
