package com.ontimize.jee.sdms.common.zip;

import java.io.InputStream;
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
     * The inputStream field represents the input stream to be zipped.
     */
    private InputStream inputStream;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsZipData() {
    }

    public OSdmsZipData( final String fileName, final InputStream inputStream ) {
        this.setFileName( fileName );
        this.setInputStream( inputStream );
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

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public void setInputStream( final InputStream inputStream ) {
        this.inputStream = inputStream;
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
