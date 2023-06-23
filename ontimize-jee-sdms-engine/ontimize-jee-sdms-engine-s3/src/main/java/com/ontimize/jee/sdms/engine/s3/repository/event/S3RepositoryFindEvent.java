package com.ontimize.jee.sdms.engine.s3.repository.event;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.ontimize.jee.sdms.common.event.IOSdmsEvent;

import java.util.Arrays;
import java.util.List;

public class S3RepositoryFindEvent implements IOSdmsEvent {

    private final List<ListObjectsRequest> requests;

    public S3RepositoryFindEvent( final List<ListObjectsRequest> requests ) {
        this.requests = requests;
    }

    public S3RepositoryFindEvent( final ListObjectsRequest request ) {
        this.requests = Arrays.asList( request );
    }

    public List<ListObjectsRequest> getRequests() {
        return this.requests;
    }

    @Override
    public String toString(){
        return new StringBuilder( "S3RepositoryFindEvent{" )
                .append( "requests=" ).append( this.getRequests() )
                .append( '}' )
                .toString();
    }
}
