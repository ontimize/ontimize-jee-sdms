package com.ontimize.jee.sdms.event.listener.s3.repository;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryMoveEvent;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;


@OSdmsEventListener
public class S3RepositoryMoveEventListener extends IOSdmsEventListener<S3RepositoryMoveEvent> {
    private static final Class<S3RepositoryMoveEvent> EVENT = S3RepositoryMoveEvent.class;

    @Override
    public Class<S3RepositoryMoveEvent> getEvent() {
        return EVENT;
    }
}