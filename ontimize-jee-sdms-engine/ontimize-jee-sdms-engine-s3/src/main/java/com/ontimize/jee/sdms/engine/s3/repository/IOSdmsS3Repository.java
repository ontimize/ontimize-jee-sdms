package com.ontimize.jee.sdms.engine.s3.repository;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ontimize.jee.sdms.engine.s3.repository.dto.OSdmsS3RepositoryDto;
import com.ontimize.jee.sdms.engine.s3.repository.response.OSdmsS3RepositoryResponse;

import java.util.List;


/**
 * Interface that defines the methods to realize the operations with the S3 API.
 */
public interface IOSdmsS3Repository {

    //Parameters Names Constants
    String PARAM_REQUEST = "request";
    String PARAM_BUCKET = "bucket";
    String PARAM_KEY = "key";
    String PARAM_PREFIX = "prefix";
    String PARAM_CURRENT_PREFIX = "current_prefix";


    /**
     * Method that finds the S3 objects that match with a request.
     *
     * @param request The request to find the S3 objects
     *
     * @return The S3 repository response with the information of the S3 objects
     *
     * @see ListObjectsRequest
     * @see OSdmsS3RepositoryResponse
     * @see OSdmsS3RepositoryDto
     */
    OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> find( ListObjectsRequest request );


    /**
     * Method that finds the S3 objects that match with a list of requests.
     *
     * @param requests The list of requests to find the S3 objects
     *
     * @return The S3 repository response with the information of the S3 objects
     *
     * @see ListObjectsRequest
     * @see OSdmsS3RepositoryResponse
     * @see OSdmsS3RepositoryDto
     */
    OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> find( List<ListObjectsRequest> requests );


    /**
     * Method that downloads the S3 objects that match with a request.
     *
     * @param request The request to download the S3 objects
     *
     * @return The S3 repository response with the information of the S3 objects
     *
     * @see ListObjectsRequest
     * @see OSdmsS3RepositoryResponse
     * @see OSdmsS3RepositoryDto
     */
    OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> download( ListObjectsRequest request );


    /**
     * Method that downloads the S3 objects that match with a list of requests.
     *
     * @param requests The list of requests to download the S3 objects
     *
     * @return The S3 repository response with the information of the S3 objects
     *
     * @see ListObjectsRequest
     * @see OSdmsS3RepositoryResponse
     * @see OSdmsS3RepositoryDto
     */
    OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> download( List<ListObjectsRequest> requests );


    /**
     * Method that uploads the S3 objects that match with a request.
     *
     * @param request The request to upload the S3 objects
     *
     * @return The S3 repository response with the information of the S3 objects
     *
     * @see PutObjectRequest
     * @see OSdmsS3RepositoryResponse
     * @see OSdmsS3RepositoryDto
     */
    OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> upload( PutObjectRequest request );


    /**
     * Method that uploads the S3 objects that match with a list of requests.
     *
     * @param requests The list of requests to upload the S3 objects
     *
     * @return The S3 repository response with the information of the S3 objects
     *
     * @see PutObjectRequest
     * @see OSdmsS3RepositoryResponse
     * @see OSdmsS3RepositoryDto
     */
    OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> upload( List<PutObjectRequest> requests );


    /**
     * Method that copies the S3 objects that match with a request.
     *
     * @param request The request to copy the S3 objects
     * @param bucket  The bucket to be copied the S3 objects
     * @param key     The new key of the copied S3 object
     *
     * @return The S3 repository response with the information of the S3 objects
     *
     * @see ListObjectsRequest
     * @see OSdmsS3RepositoryResponse
     * @see OSdmsS3RepositoryDto
     */
    OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> copy( ListObjectsRequest request, String bucket, String key );


    /**
     * Method that copìes the S3 objects that match with a list of requests.
     *
     * @param requests      The list of requests to copy the S3 objects
     * @param bucket        The bucket to be copied the S3 objects
     * @param prefix        The prefix to save the copied S3 objects
     * @param currentPrefix The current prefix from where the S3 objects are copied
     *
     * @return The S3 repository response with the information of the S3 objects
     *
     * @see ListObjectsRequest
     * @see OSdmsS3RepositoryResponse
     * @see OSdmsS3RepositoryDto
     */
    OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> copyAll( List<ListObjectsRequest> requests, String bucket, String prefix, String currentPrefix );


    /**
     * Method that moves the S3 objects that match with a request.
     *
     * @param request The request to move the S3 objects
     * @param bucket  The bucket to be moved the S3 objects
     * @param key     The new key of the moved S3 object
     *
     * @return The S3 repository response with the information of the S3 objects
     *
     * @see ListObjectsRequest
     * @see OSdmsS3RepositoryResponse
     * @see OSdmsS3RepositoryDto
     */
    OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> move( ListObjectsRequest request, String bucket, String key );


    /**
     * Method that moves the S3 objects that match with a list of requests.
     *
     * @param requests      The list of requests to move the S3 objects
     * @param bucket        The bucket to be moved the S3 objects
     * @param prefix        The prefix to save the moved S3 objects
     * @param currentPrefix The current prefix from where the S3 objects are moved
     *
     * @return The S3 repository response with the information of the S3 objects
     *
     * @see ListObjectsRequest
     * @see OSdmsS3RepositoryResponse
     * @see OSdmsS3RepositoryDto
     */
    OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> moveAll( List<ListObjectsRequest> requests, String bucket, String prefix, String currentPrefix );


    /**
     * Method that deletes the S3 objects that match with a request.
     *
     * @param request The request to delete the S3 objects
     *
     * @return The S3 repository response with the information of the S3 objects
     *
     * @see ListObjectsRequest
     * @see OSdmsS3RepositoryResponse
     * @see OSdmsS3RepositoryDto
     */
    OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> delete( ListObjectsRequest request );


    /**
     * Method that deletes the S3 objects that match with a list of requests.
     *
     * @param requests The list of requests to delete the S3 objects
     *
     * @return The S3 repository response with the information of the S3 objects
     *
     * @see ListObjectsRequest
     * @see OSdmsS3RepositoryResponse
     * @see OSdmsS3RepositoryDto
     */
    OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> delete( List<ListObjectsRequest> requests );

}
