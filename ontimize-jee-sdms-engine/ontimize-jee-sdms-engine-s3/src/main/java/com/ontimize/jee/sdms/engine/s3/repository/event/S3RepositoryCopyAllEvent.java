package com.ontimize.jee.sdms.engine.s3.repository.event;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.ontimize.jee.sdms.common.event.IOSdmsEvent;

import java.util.Arrays;
import java.util.List;

public class S3RepositoryCopyAllEvent implements IOSdmsEvent {

    private final List<ListObjectsRequest> requests;
    private String bucket;
    private String prefix;
    private String currentPrefix;

    public S3RepositoryCopyAllEvent( final List<ListObjectsRequest> requests, final String bucket, final String prefix, final String currentPrefix ) {
        this.requests = requests;
        this.bucket = bucket;
        this.prefix = prefix;
        this.currentPrefix = currentPrefix;
    }

    public List<ListObjectsRequest> getRequests() {
        return this.requests;
    }

    public String getBucket() {
        return this.bucket;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getCurrentPrefix() {
        return this.currentPrefix;
    }

    @Override
    public String toString(){
        return new StringBuilder( "S3RepositoryCopyAllEvent{" )
                .append( "requests=" ).append( this.getRequests() )
                .append( ", bucket=" ).append( this.getBucket() )
                .append( ", prefix=" ).append( this.getPrefix() )
                .append( ", currentPrefix=" ).append( this.getCurrentPrefix() )
                .append( '}' )
                .toString();
    }
}
