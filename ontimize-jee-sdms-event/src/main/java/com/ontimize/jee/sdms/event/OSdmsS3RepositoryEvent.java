package com.ontimize.jee.sdms.event;


/**
 * This enum represents the possible events that can be triggered by the S3 repository.
 */
public enum OSdmsS3RepositoryEvent {

    FIND,
    GET_METADATA,
    DOWNLOAD,
    UPLOAD,
    COPY,
    MOVE,
    DELETE,
    EXISTS;

}
