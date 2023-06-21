package com.ontimize.jee.sdms.engine.s3.util.response.mapper;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.sdms.common.response.builder.IOSdmsMappeable;
import com.ontimize.jee.sdms.engine.s3.repository.response.OSdmsS3RepositoryResponse;


/**
 * Interface to map the response from the S3 repository to an Ontimize EntityResult
 *
 * @see EntityResult
 */
public interface IOSdmsS3ResponseMapper {

    /**
     * Maps the response from the S3 repository to an Ontimize EntityResult
     *
     * @param response The response from the S3 repository
     *
     * @return The Ontimize EntityResult
     *
     * @see IOSdmsMappeable
     * @see OSdmsS3RepositoryResponse
     * @see EntityResult
     */
    <T extends IOSdmsMappeable> EntityResult map( OSdmsS3RepositoryResponse<T> response );


    /**
     * Maps the data to an Ontimize EntityResult
     *
     * @param data The data to map
     *
     * @return The Ontimize EntityResult
     *
     * @see IOSdmsMappeable
     * @see EntityResult
     */
    <T extends IOSdmsMappeable> EntityResult map( T data );

}
