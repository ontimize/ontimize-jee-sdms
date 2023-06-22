package com.ontimize.jee.sdms.event.listener.s3.repository;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryCopyAllEvent;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryCopyEvent;
import com.ontimize.jee.sdms.engine.s3.repository.event.S3RepositoryUploadEvent;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.event.listener.service.OSdmsServiceCopyEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class S3RepositoryCopyEventListener extends IOSdmsEventListener<S3RepositoryCopyEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<S3RepositoryCopyEvent> EVENT = S3RepositoryCopyEvent.class;

    @Override
    public Class<S3RepositoryCopyEvent> getEvent() {
        return EVENT;
    }
}