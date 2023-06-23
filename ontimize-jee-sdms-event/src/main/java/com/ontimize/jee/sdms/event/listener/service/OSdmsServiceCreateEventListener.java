package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceCreateEvent;


@OSdmsEventListener
public class OSdmsServiceCreateEventListener extends IOSdmsEventListener<OSdmsServiceCreateEvent> {
    private static final Class<OSdmsServiceCreateEvent> EVENT = OSdmsServiceCreateEvent.class;

    @Override
    public Class<OSdmsServiceCreateEvent> getEvent() {
        return EVENT;
    }
}