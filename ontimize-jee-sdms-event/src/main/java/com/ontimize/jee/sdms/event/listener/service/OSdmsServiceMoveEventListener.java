package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceCopyEvent;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceFindEvent;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceMoveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class OSdmsServiceMoveEventListener extends IOSdmsEventListener<OSdmsServiceMoveEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<OSdmsServiceMoveEvent> EVENT = OSdmsServiceMoveEvent.class;

    @Override
    public Class<OSdmsServiceMoveEvent> getEvent() {
        return EVENT;
    }
}