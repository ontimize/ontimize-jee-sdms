package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceFindEvent;


@OSdmsEventListener
public class OSdmsServiceFindEventListener extends IOSdmsEventListener<OSdmsServiceFindEvent> {
    private static final Class<OSdmsServiceFindEvent> EVENT = OSdmsServiceFindEvent.class;

    @Override
    public Class<OSdmsServiceFindEvent> getEvent() {
        return EVENT;
    }
}