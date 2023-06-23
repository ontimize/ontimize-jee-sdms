package com.ontimize.jee.sdms.event.listener.service;

import com.ontimize.jee.sdms.common.event.IOSdmsEventListener;
import com.ontimize.jee.sdms.event.annotation.OSdmsEventListener;
import com.ontimize.jee.sdms.server.service.event.OSdmsServiceCopyEvent;


@OSdmsEventListener
public class OSdmsServiceCopyEventListener extends IOSdmsEventListener<OSdmsServiceCopyEvent> {
    private static final Class<OSdmsServiceCopyEvent> EVENT = OSdmsServiceCopyEvent.class;

    @Override
    public Class<OSdmsServiceCopyEvent> getEvent() {
        return EVENT;
    }
}