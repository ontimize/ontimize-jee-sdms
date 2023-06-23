package com.ontimize.jee.sdms.server.service.event;

import com.ontimize.jee.sdms.common.dto.OSdmsRestDataDto;
import com.ontimize.jee.sdms.common.event.IOSdmsEvent;

public class OSdmsServiceFindEvent implements IOSdmsEvent {
    private final OSdmsRestDataDto data;

    public OSdmsServiceFindEvent( final OSdmsRestDataDto data ) {
        this.data = data;
    }

    public OSdmsRestDataDto getData() {
        return this.data;
    }

    @Override
    public String toString(){
        return new StringBuilder( "OSdmsServiceFindEvent{" )
                .append( "data=" ).append( this.getData() )
                .append( '}' )
                .toString();
    }
}
