package com.ontimize.jee.sdms.event.listener.s3.repository;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryDeleteEvent;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryMoveEvent;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryUploadEvent;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.event.listener.service.OSdmsServiceCopyEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class S3RepositoryUploadEventListener extends IOSdmsEventListener<S3RepositoryUploadEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<S3RepositoryUploadEvent> EVENT = S3RepositoryUploadEvent.class;

    @Override
    public Class<S3RepositoryUploadEvent> getEvent() {
        return EVENT;
    }
}