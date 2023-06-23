package com.ontimize.jee.sdms.event.listener.s3.repository;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryFindEvent;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;


@OSdmsEventListener
public class S3RepositoryFindEventListener extends IOSdmsEventListener<S3RepositoryFindEvent> {
    private static final Class<S3RepositoryFindEvent> EVENT = S3RepositoryFindEvent.class;

    @Override
    public Class<S3RepositoryFindEvent> getEvent() {
        return EVENT;
    }
}