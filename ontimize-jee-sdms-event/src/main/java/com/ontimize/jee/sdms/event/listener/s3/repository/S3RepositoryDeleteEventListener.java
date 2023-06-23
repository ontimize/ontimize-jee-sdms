package com.ontimize.jee.sdms.event.listener.s3.repository;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryDeleteEvent;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;


@OSdmsEventListener
public class S3RepositoryDeleteEventListener extends IOSdmsEventListener<S3RepositoryDeleteEvent> {
    private static final Class<S3RepositoryDeleteEvent> EVENT = S3RepositoryDeleteEvent.class;

    @Override
    public Class<S3RepositoryDeleteEvent> getEvent() {
        return EVENT;
    }
}