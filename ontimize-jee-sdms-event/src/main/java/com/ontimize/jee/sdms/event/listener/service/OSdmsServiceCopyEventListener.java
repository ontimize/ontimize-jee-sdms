package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceCopyEvent;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class OSdmsServiceCopyEventListener extends IOSdmsEventListener<OSdmsServiceCopyEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<OSdmsServiceCopyEvent> EVENT = OSdmsServiceCopyEvent.class;

    @Override
    public Class<OSdmsServiceCopyEvent> getEvent() {
        return EVENT;
    }
}