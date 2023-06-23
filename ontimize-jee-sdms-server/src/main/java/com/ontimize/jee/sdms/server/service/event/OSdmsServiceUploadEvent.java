package com.ontimize.jee.sdms.server.service.event;

import com.ontimize.jee.sdms.common.dto.OSdmsRestDataDto;
import com.ontimize.jee.sdms.common.event.IOSdmsEvent;
import org.springframework.web.multipart.MultipartFile;

public class OSdmsServiceUploadEvent implements IOSdmsEvent {
    private final OSdmsRestDataDto data;
    private final MultipartFile file;

    public OSdmsServiceUploadEvent( final OSdmsRestDataDto data, final MultipartFile file ) {
        this.data = data;
        this.file = file;
    }

    public OSdmsRestDataDto getData() {
        return this.data;
    }

    public MultipartFile getFile() {
        return this.file;
    }

    @Override
    public String toString(){
        return new StringBuilder( "OSdmsServiceUploadEvent{" )
                .append( "data=" ).append( this.getData() )
                .append( ", file=" ).append( this.getFile() )
                .append( '}' )
                .toString();
    }
}
