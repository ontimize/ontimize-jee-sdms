package com.ontimize.jee.sdms.event.listener.s3.repository;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryCopyEvent;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryDeleteEvent;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryDownloadEvent;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.event.listener.service.OSdmsServiceCopyEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class S3RepositoryDeleteEventListener extends IOSdmsEventListener<S3RepositoryDeleteEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<S3RepositoryDeleteEvent> EVENT = S3RepositoryDeleteEvent.class;

    @Override
    public Class<S3RepositoryDeleteEvent> getEvent() {
        return EVENT;
    }
}