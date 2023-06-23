package com.ontimize.jee.sdms.engine.s3.repository.event;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ontimize.jee.sdms.common.event.IOSdmsEvent;

import java.util.Arrays;
import java.util.List;

public class S3RepositoryUploadEvent implements IOSdmsEvent {

    private final List<PutObjectRequest> requests;

    public S3RepositoryUploadEvent( final List<PutObjectRequest> requests ) {
        this.requests = requests;
    }

    public S3RepositoryUploadEvent( final PutObjectRequest request ) {
        this.requests = Arrays.asList( request );
    }

    public List<PutObjectRequest> getRequests() {
        return this.requests;
    }

    @Override
    public String toString(){
        return new StringBuilder( "S3RepositoryUploadEvent{" )
                .append( "requests=" ).append( this.getRequests() )
                .append( '}' )
                .toString();
    }
}
