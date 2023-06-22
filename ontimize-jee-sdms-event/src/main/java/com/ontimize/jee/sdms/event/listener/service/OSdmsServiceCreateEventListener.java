package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceCopyEvent;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceCreateEvent;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceDeleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class OSdmsServiceCreateEventListener extends IOSdmsEventListener<OSdmsServiceCreateEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<OSdmsServiceCreateEvent> EVENT = OSdmsServiceCreateEvent.class;

    @Override
    public Class<OSdmsServiceCreateEvent> getEvent() {
        return EVENT;
    }
}