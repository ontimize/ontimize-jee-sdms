package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceCreateEvent;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceUpdateEvent;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class OSdmsServiceUploadEventListener extends IOSdmsEventListener<OSdmsServiceUploadEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<OSdmsServiceUploadEvent> EVENT = OSdmsServiceUploadEvent.class;

    @Override
    public Class<OSdmsServiceUploadEvent> getEvent() {
        return EVENT;
    }
}