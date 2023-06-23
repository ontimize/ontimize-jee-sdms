package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceUploadEvent;


@OSdmsEventListener
public class OSdmsServiceUploadEventListener extends IOSdmsEventListener<OSdmsServiceUploadEvent> {
    private static final Class<OSdmsServiceUploadEvent> EVENT = OSdmsServiceUploadEvent.class;

    @Override
    public Class<OSdmsServiceUploadEvent> getEvent() {
        return EVENT;
    }
}