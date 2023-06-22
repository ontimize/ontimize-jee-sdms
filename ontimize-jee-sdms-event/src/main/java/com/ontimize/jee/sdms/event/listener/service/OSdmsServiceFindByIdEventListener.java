package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceDownloadEvent;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceFindByIdEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class OSdmsServiceFindByIdEventListener extends IOSdmsEventListener<OSdmsServiceFindByIdEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<OSdmsServiceFindByIdEvent> EVENT = OSdmsServiceFindByIdEvent.class;

    @Override
    public Class<OSdmsServiceFindByIdEvent> getEvent() {
        return EVENT;
    }
}