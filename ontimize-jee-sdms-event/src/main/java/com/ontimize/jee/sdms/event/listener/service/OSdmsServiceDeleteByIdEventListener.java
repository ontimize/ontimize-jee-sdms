package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceDeleteByIdEvent;


@OSdmsEventListener
public class OSdmsServiceDeleteByIdEventListener extends IOSdmsEventListener<OSdmsServiceDeleteByIdEvent> {
    private static final Class<OSdmsServiceDeleteByIdEvent> EVENT = OSdmsServiceDeleteByIdEvent.class;

    @Override
    public Class<OSdmsServiceDeleteByIdEvent> getEvent() {
        return EVENT;
    }
}