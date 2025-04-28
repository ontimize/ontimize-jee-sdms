package com.ontimize.jee.sdms.common.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DefaultTemporalFileManager implements TemporalFileManager{

    @Value( "${ontimize.sdms.file.temporal.directory}" )
    private String temporalDirectory;

    private static final ThreadLocal<List<File>> FILES = new ThreadLocal<>();

    @Override
    public File create( final String name, final InputStream inputStream ) throws IOException {
        final File directory = new File( this.temporalDirectory );
        final File file;
        if( directory.exists() && directory.isDirectory() ) file = File.createTempFile( name, ".tmp", directory );
        else file = File.createTempFile( name, ".tmp" );
        try( FileOutputStream fos = new FileOutputStream( file)) {
            inputStream.transferTo(fos);
        }
        if( FILES.get() == null ) FILES.set( new ArrayList<>() );
        FILES.get().add( file );
        return file;
    }

    @Override
    public File create( final InputStream inputStream ) throws IOException {
        return this.create( UUID.randomUUID().toString(), inputStream );
    }

    @Override
    public void delete( final File file ) throws IOException {
        if( FILES.get() == null ) return;
        final Optional<File> result = FILES.get().stream()
                .filter( target -> target.getAbsolutePath().equals( file.getAbsolutePath() ) )
                .findFirst();
        if( result.isPresent() ){
            final File target = result.get();
            FILES.get().remove( target );
            if( target.exists() ) Files.delete( target.toPath() );
        }
    }

    @Override
    public void cleanUp() throws IOException {
        for( final File file : FILES.get() ){
            if( file.exists() ) Files.delete( file.toPath() );
        }
        FILES.remove();
    }
}
