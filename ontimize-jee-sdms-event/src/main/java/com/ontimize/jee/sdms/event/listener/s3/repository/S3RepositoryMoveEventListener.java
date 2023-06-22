package com.ontimize.jee.sdms.event.listener.s3.repository;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryMoveAllEvent;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryMoveEvent;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.event.listener.service.OSdmsServiceCopyEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class S3RepositoryMoveEventListener extends IOSdmsEventListener<S3RepositoryMoveEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<S3RepositoryMoveEvent> EVENT = S3RepositoryMoveEvent.class;

    @Override
    public Class<S3RepositoryMoveEvent> getEvent() {
        return EVENT;
    }
}