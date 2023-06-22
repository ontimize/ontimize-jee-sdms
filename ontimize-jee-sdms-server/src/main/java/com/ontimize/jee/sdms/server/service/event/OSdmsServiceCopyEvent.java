package com.ontimize.jee.sdms.server.service.event;

import com.ontimize.jee.sdms.common.dto.OSdmsRestDataDto;
import com.ontimize.jee.sdms.common.event.IOSdmsEvent;

public class OSdmsServiceCopyEvent implements IOSdmsEvent {
    private final OSdmsRestDataDto data;

    public OSdmsServiceCopyEvent( final OSdmsRestDataDto data ) {
        this.data = data;
    }

    public OSdmsRestDataDto getData() {
        return this.data;
    }

    @Override
    public String toString(){
        return new StringBuilder( "OSdmsServiceCopyEvent{" )
                .append( "data=" ).append( this.getData() )
                .append( '}' )
                .toString();
    }
}
