package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceUpdateEvent;


@OSdmsEventListener
public class OSdmsServiceUpdateEventListener extends IOSdmsEventListener<OSdmsServiceUpdateEvent> {
    private static final Class<OSdmsServiceUpdateEvent> EVENT = OSdmsServiceUpdateEvent.class;

    @Override
    public Class<OSdmsServiceUpdateEvent> getEvent() {
        return EVENT;
    }
}