package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceUpdateEvent;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class OSdmsServiceUpdateEventListener extends IOSdmsEventListener<OSdmsServiceUpdateEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<OSdmsServiceUpdateEvent> EVENT = OSdmsServiceUpdateEvent.class;

    @Override
    public Class<OSdmsServiceUpdateEvent> getEvent() {
        return EVENT;
    }
}