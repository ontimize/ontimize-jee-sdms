package com.ontimize.jee.sdms.engine.s3.repository.event;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.ontimize.jee.sdms.common.event.IOSdmsEvent;

import java.util.Arrays;
import java.util.List;

public class S3RepositoryDeleteEvent implements IOSdmsEvent {

    private final List<ListObjectsRequest> requests;

    public S3RepositoryDeleteEvent( final List<ListObjectsRequest> requests ) {
        this.requests = requests;
    }

    public S3RepositoryDeleteEvent( final ListObjectsRequest request ) {
        this.requests = Arrays.asList( request );
    }

    public List<ListObjectsRequest> getRequests() {
        return this.requests;
    }

    @Override
    public String toString(){
        return new StringBuilder( "S3RepositoryDeleteEvent{" )
                .append( "requests=" ).append( this.getRequests() )
                .append( '}' )
                .toString();
    }
}
