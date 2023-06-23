package com.ontimize.jee.sdms.event.listener.s3.repository;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryCopyEvent;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;


@OSdmsEventListener
public class S3RepositoryCopyEventListener extends IOSdmsEventListener<S3RepositoryCopyEvent> {
    private static final Class<S3RepositoryCopyEvent> EVENT = S3RepositoryCopyEvent.class;

    @Override
    public Class<S3RepositoryCopyEvent> getEvent() {
        return EVENT;
    }
}