package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceMoveEvent;


@OSdmsEventListener
public class OSdmsServiceMoveEventListener extends IOSdmsEventListener<OSdmsServiceMoveEvent> {
    private static final Class<OSdmsServiceMoveEvent> EVENT = OSdmsServiceMoveEvent.class;

    @Override
    public Class<OSdmsServiceMoveEvent> getEvent() {
        return EVENT;
    }
}