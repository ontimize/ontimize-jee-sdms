package com.ontimize.jee.sdms.engine.s3.repository.event;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.ontimize.jee.sdms.common.event.IOSdmsEvent;

public class S3RepositoryMoveEvent implements IOSdmsEvent {

    private final ListObjectsRequest request;
    private String bucket;
    private String key;

    public S3RepositoryMoveEvent( final ListObjectsRequest request, final String bucket, final String key ) {
        this.request = request;
        this.bucket = bucket;
        this.key = key;
    }

    public ListObjectsRequest getRequest() {
        return this.request;
    }

    public String getBucket() {
        return this.bucket;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public String toString(){
        return new StringBuilder( "S3RepositoryMoveEvent{" )
                .append( "request=" ).append( this.getRequest() )
                .append( ", bucket=" ).append( this.getBucket() )
                .append( ", key=" ).append( this.getKey() )
                .append( '}' )
                .toString();
    }
}
