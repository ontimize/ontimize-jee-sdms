package com.ontimize.jee.sdms.event.listener.s3.repository;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryCopyAllEvent;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryCopyEvent;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.event.listener.service.OSdmsServiceCopyEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class S3RepositoryCopyAllEventListener extends IOSdmsEventListener<S3RepositoryCopyAllEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<S3RepositoryCopyAllEvent> EVENT = S3RepositoryCopyAllEvent.class;

    @Override
    public Class<S3RepositoryCopyAllEvent> getEvent() {
        return EVENT;
    }
}