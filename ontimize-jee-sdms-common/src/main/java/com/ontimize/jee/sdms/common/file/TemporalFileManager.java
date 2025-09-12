package com.ontimize.jee.sdms.common.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface TemporalFileManager {

    File create( InputStream inputStream ) throws IOException;
    File create( String name, InputStream inputStream ) throws IOException;
    void delete( File file ) throws IOException;
    void cleanUp() throws IOException;
}
