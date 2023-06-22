package com.ontimize.jee.sdms.server.service.event;

import com.ontimize.jee.sdms.common.dto.OSdmsRestDataDto;
import com.ontimize.jee.sdms.common.event.IOSdmsEvent;

import java.io.Serializable;

public class OSdmsServiceFindByIdEvent implements IOSdmsEvent {

    private final Serializable id;
    private final OSdmsRestDataDto data;

    public OSdmsServiceFindByIdEvent( final Serializable id, final OSdmsRestDataDto data ) {
        this.id = id;
        this.data = data;
    }

    public Serializable getId() {
        return this.id;
    }

    public OSdmsRestDataDto getData() {
        return this.data;
    }

    @Override
    public String toString(){
        return new StringBuilder( "OSdmsServiceFindByIdEvent{" )
                .append( "id=" ).append( this.getId() )
                .append( ", data=" ).append( this.getData() )
                .append( '}' )
                .toString();
    }
}
