package com.ontimize.jee.sdms.rest.filter;

import com.ontimize.jee.sdms.common.file.TemporalFileManager;
import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TemporalFilesFilter implements Filter{

    private final TemporalFileManager temporalFileManager;

    public TemporalFilesFilter( final TemporalFileManager temporalFileManager ) {
        this.temporalFileManager = temporalFileManager;
    }

    @Override
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain
    ) throws IOException, ServletException {
        try{
            filterChain.doFilter( servletRequest, servletResponse );
        }
        finally {
            this.temporalFileManager.cleanUp();
        }
    }
}
