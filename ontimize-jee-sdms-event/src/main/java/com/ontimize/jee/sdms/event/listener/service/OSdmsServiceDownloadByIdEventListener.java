package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceDeleteEvent;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceDownloadByIdEvent;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceDownloadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class OSdmsServiceDownloadByIdEventListener extends IOSdmsEventListener<OSdmsServiceDownloadByIdEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<OSdmsServiceDownloadByIdEvent> EVENT = OSdmsServiceDownloadByIdEvent.class;

    @Override
    public Class<OSdmsServiceDownloadByIdEvent> getEvent() {
        return EVENT;
    }
}