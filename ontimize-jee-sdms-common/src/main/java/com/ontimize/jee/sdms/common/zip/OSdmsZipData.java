package com.ontimize.jee.sdms.common.zip;

import java.io.File;
import java.util.Objects;


/**
 * This class represents the data that will be compressed by the {@link IOSdmsZipCompressor}.
 */
public class OSdmsZipData {

    /**
     * The fileName field represents the name of the file that is contained in the zip file.
     */
    private String fileName;

    /**
     * The File field represents the file to be zipped.
     */
    private File file;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsZipData() {
    }

    public OSdmsZipData( final String fileName, final File file ) {
        this.setFileName( fileName );
        this.setFile( file );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| GETTERS AND SETTERS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName( final String fileName ) {
        this.fileName = fileName;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile( final File file ) {
        this.file = file;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| EQUALS AND HASHCODE |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean equals( final Object obj ) {
        if( this == obj ) return true;
        if( obj == null || getClass() != obj.getClass() ) return false;
        final OSdmsZipData target = ( OSdmsZipData ) obj;
        return Objects.equals( this.fileName, target.fileName );
    }

    @Override
    public int hashCode() {
        return Objects.hash( this.fileName );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
}
