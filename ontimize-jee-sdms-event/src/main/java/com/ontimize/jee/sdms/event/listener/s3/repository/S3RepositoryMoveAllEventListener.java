package com.ontimize.jee.sdms.event.listener.s3.repository;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryCopyAllEvent;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryFindEvent;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryMoveAllEvent;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.event.listener.service.OSdmsServiceCopyEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class S3RepositoryMoveAllEventListener extends IOSdmsEventListener<S3RepositoryMoveAllEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<S3RepositoryMoveAllEvent> EVENT = S3RepositoryMoveAllEvent.class;

    @Override
    public Class<S3RepositoryMoveAllEvent> getEvent() {
        return EVENT;
    }
}