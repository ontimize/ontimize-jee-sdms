package com.ontimize.jee.sdms.engine.s3.util.normalize;

import org.springframework.stereotype.Component;

@Component( "OSdmsS3KeyNormalize" )
public class OSdmsS3KeyNormalize implements IOSdmsS3KeyNormalize{
    @Override
    public String normalize( final String target ) {
        String result = target;
        if( result != null && result.startsWith( "/" ) ) {
            result = result.substring( 1 );
        }
        return result;
    }
}
