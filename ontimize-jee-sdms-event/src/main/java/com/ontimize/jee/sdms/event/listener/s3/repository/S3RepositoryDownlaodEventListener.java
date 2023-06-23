package com.ontimize.jee.sdms.event.listener.s3.repository;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryDownloadEvent;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;


@OSdmsEventListener
public class S3RepositoryDownlaodEventListener extends IOSdmsEventListener<S3RepositoryDownloadEvent> {
    private static final Class<S3RepositoryDownloadEvent> EVENT = S3RepositoryDownloadEvent.class;

    @Override
    public Class<S3RepositoryDownloadEvent> getEvent() {
        return EVENT;
    }
}