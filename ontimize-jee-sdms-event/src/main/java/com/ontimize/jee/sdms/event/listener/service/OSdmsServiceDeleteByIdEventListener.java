package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceCreateEvent;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceDeleteByIdEvent;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceDownloadByIdEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OSdmsEventListener
public class OSdmsServiceDeleteByIdEventListener extends IOSdmsEventListener<OSdmsServiceDeleteByIdEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsServiceCopyEventListener.class );
    private static final Class<OSdmsServiceDeleteByIdEvent> EVENT = OSdmsServiceDeleteByIdEvent.class;

    @Override
    public Class<OSdmsServiceDeleteByIdEvent> getEvent() {
        return EVENT;
    }
}