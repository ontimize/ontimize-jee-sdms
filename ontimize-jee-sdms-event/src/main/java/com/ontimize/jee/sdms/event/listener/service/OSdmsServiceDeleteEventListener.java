package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceDeleteEvent;


@OSdmsEventListener
public class OSdmsServiceDeleteEventListener extends IOSdmsEventListener<OSdmsServiceDeleteEvent> {
    private static final Class<OSdmsServiceDeleteEvent> EVENT = OSdmsServiceDeleteEvent.class;

    @Override
    public Class<OSdmsServiceDeleteEvent> getEvent() {
        return EVENT;
    }
}