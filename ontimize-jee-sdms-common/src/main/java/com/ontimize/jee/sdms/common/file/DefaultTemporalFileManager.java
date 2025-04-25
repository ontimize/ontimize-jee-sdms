package com.ontimize.jee.sdms.common.file;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

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
@RequestScope
public class DefaultTemporalFileManager implements TemporalFileManager{

    @Value( "${ontimize.sdms.file.temporal.directory}" )
    private String temporalDirectory;

    private final List<File> files = new ArrayList<>();

    @Override
    public File create( final String name, final InputStream inputStream ) throws IOException {
        final File directory = new File( this.temporalDirectory );
        final File file;
        if( directory.exists() && directory.isDirectory() ) file = File.createTempFile( name, ".tmp", directory );
        else file = File.createTempFile( name, ".tmp" );
        try( FileOutputStream fos = new FileOutputStream( file)) {
            inputStream.transferTo(fos);
        }
        this.files.add( file );
        return file;
    }

    @Override
    public File create( final InputStream inputStream ) throws IOException {
        return this.create( UUID.randomUUID().toString(), inputStream );
    }

    @Override
    public void delete( final File file ) throws IOException {
        final Optional<File> result = this.files.stream()
                .filter( target -> target.getAbsolutePath().equals( file.getAbsolutePath() ) )
                .findFirst();
        if( result.isPresent() ){
            final File target = result.get();
            this.files.remove( target );
            if( target.exists() ) Files.delete( target.toPath() );
        }
    }

    @PreDestroy
    @Override
    public void cleanUp() throws IOException {
        for( final File file : this.files ){
            if( file.exists() ) Files.delete( file.toPath() );
        }
    }
}
