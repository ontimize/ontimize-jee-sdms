package com.ontimize.jee.sdms.common.zip;

import com.ontimize.jee.sdms.common.response.builder.IOSdmsMappeable;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * The OSdmsZipDto class represents a data transfer object (DTO) used by the {@link OSdmsZipCompressor} class to return
 * multiple files compressed in ZIP format with the zip file name and size.
 */
public class OSdmsZipDto implements IOSdmsMappeable {

    /** The file field represents the ZIP file as a byte array. */
    private InputStream file;


    /** The name field represents the name of the ZIP file. */
    private String name;


    /** The size field represents the size of the ZIP file. */
    private Long size;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsZipDto() {
    }

    public OSdmsZipDto( final InputStream file, final String name, final Long size ) {
        this.setFile( file );
        this.setName( name );
        this.setSize( size );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| GETTERS AND SETTERS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\


    public InputStream getFile() {
        return this.file;
    }

    public void setFile( final InputStream file ) {
        this.file = file;
    }

    public String getName() {
        return this.name;
    }

    public void setName( final String name ) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    /**
     * Sets the size of the compressed file.
     *
     * @param size The size of the compressed file as Integer.
     */
    public void setSize( final Integer size ) {
        this.size = size.longValue();
    }

    /**
     * Sets the size of the compressed file.
     *
     * @param size The size of the compressed file as Long.
     */
    public void setSize( final Long size ) {
        this.size = size;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| EQUALS AND HASHCODE |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;
        OSdmsZipDto that = ( OSdmsZipDto ) o;
        return Objects.equals( this.file, that.file ) && Objects.equals( this.name, that.name ) && Objects.equals(
                this.size, that.size );
    }

    @Override
    public int hashCode() {
        return Objects.hash( this.file, this.name, this.size );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public Map<?, ?> toMap() {
        final Map<String, Object> result = new HashMap<>();

        if( this.name != null ) result.put( "name", this.name );
        if( this.file != null ) result.put( "file", this.file );
        if( this.size != null ) result.put( "size", this.size );

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
