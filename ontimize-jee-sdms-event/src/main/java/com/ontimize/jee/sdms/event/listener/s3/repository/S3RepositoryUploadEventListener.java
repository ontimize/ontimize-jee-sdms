package com.ontimize.jee.sdms.event.listener.s3.repository;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryUploadEvent;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;


@OSdmsEventListener
public class S3RepositoryUploadEventListener extends IOSdmsEventListener<S3RepositoryUploadEvent> {
    private static final Class<S3RepositoryUploadEvent> EVENT = S3RepositoryUploadEvent.class;

    @Override
    public Class<S3RepositoryUploadEvent> getEvent() {
        return EVENT;
    }
}