package com.ontimize.jee.sdms.engine.s3.repository.response.builder;

import com.ontimize.jee.sdms.engine.s3.repository.response.OSdmsS3RepositoryResponse;
import com.ontimize.jee.sdms.engine.s3.repository.response.codes.OSdmsS3RepositoryResponseCodes;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;



/**
 * Implementation of the {@link IOSdmsS3RepositoryResponseBuilder} interface.
 *
 * @see IOSdmsS3RepositoryResponseBuilder
 */
@Component( "OSdmsS3RepositoryResponseBuilder" )
public class OSdmsS3RepositoryResponseBuilder implements IOSdmsS3RepositoryResponseBuilder {

    /** The response code to set in the S3 repository response. */
    private OSdmsS3RepositoryResponseCodes code;

    /** The response message to set in the S3 repository response. */
    private String message;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3RepositoryResponseBuilder() {
        super();
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public IOSdmsS3RepositoryResponseBuilder code(final OSdmsS3RepositoryResponseCodes code ) {
        this.code = code;
        return this;
    }

    @Override
    public IOSdmsS3RepositoryResponseBuilder message(final String message ) {
        this.message = message;
        return this;
    }

    @Override
    public OSdmsS3RepositoryResponse build() {
        final OSdmsS3RepositoryResponse result = new OSdmsS3RepositoryResponse( this.code, this.message );
        this.clear();
        return result;
    }

    @Override
    public <T> OSdmsS3RepositoryResponse<T> build(final T data ) {
        final OSdmsS3RepositoryResponse<T> result = new OSdmsS3RepositoryResponse(this.code, this.message, Arrays.asList(data));
        this.clear();
        return result;
    }

    @Override
    public <T> OSdmsS3RepositoryResponse<T> build(final List<T> data ) {
        final OSdmsS3RepositoryResponse<T> result = new OSdmsS3RepositoryResponse( this.code, this.message, data );
        this.clear();
        return result;
    }

    @Override
    public <T> OSdmsS3RepositoryResponse<T> build(final Set<T> data ) {
        return this.build( new ArrayList<>( data ));
    }
// ------------------------------------------------------------------------------------------------------------------ \\
// ------| UTILITIES |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Method that clears the information in builder.
     */
    private void clear(){
        this.code = null;
        this.message = null;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
