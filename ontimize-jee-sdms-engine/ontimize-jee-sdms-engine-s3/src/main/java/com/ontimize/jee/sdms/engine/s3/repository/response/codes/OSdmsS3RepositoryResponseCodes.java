package com.ontimize.jee.sdms.engine.s3.repository.response.codes;

import com.ontimize.jee.common.dto.EntityResult;


/**
 * This enum represents the diferent codes to be used the S3 Repository response.
 */
public enum OSdmsS3RepositoryResponseCodes {

    OK( 0, EntityResult.OPERATION_SUCCESSFUL ),
    ERROR( 1, EntityResult.OPERATION_WRONG ),
    NOT_FOUND( 2, EntityResult.OPERATION_SUCCESSFUL );

// ------------------------------------------------------------------------------------------------------------------ \\

    /** The code of the S3 Repository response */
    private final Integer code;

    /** The entity result code to map the S3 Repository response */
    private final Integer entityResultCode;

// ------------------------------------------------------------------------------------------------------------------ \\

    OSdmsS3RepositoryResponseCodes( final Integer code, final Integer entityResultCode ) {
        this.code = code;
        this.entityResultCode = entityResultCode;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    public Integer getCode() {
        return this.code;
    }

    public Integer getEntityResultCode() {
        return this.entityResultCode;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
