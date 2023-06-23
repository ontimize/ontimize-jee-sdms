package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceDownloadEvent;


@OSdmsEventListener
public class OSdmsServiceDownloadEventListener extends IOSdmsEventListener<OSdmsServiceDownloadEvent> {
    private static final Class<OSdmsServiceDownloadEvent> EVENT = OSdmsServiceDownloadEvent.class;

    @Override
    public Class<OSdmsServiceDownloadEvent> getEvent() {
        return EVENT;
    }
}