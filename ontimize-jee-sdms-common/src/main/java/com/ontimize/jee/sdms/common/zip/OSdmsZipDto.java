package com.ontimize.jee.sdms.common.zip;

import com.ontimize.jee.sdms.common.response.builder.IOSdmsMappeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * The OSdmsZipDto class represents a data transfer object (DTO) used by the {@link OSdmsZipCompressor} class to return
 * multiple files compressed in ZIP format with the zip file name and size.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class OSdmsZipDto implements IOSdmsMappeable {

    /** The file field represents the ZIP file as a byte array. */
    private InputStream file;


    /** The name field represents the name of the ZIP file. */
    private String name;


    /** The size field represents the size of the ZIP file. */
    private Long size;

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Sets the size of the compressed file.
     *
     * @param size The size of the compressed file as Integer.
     */
    public void setSize( final Integer size ){
        this.size = size.longValue();
    }

    /**
     * Sets the size of the compressed file.
     *
     * @param size The size of the compressed file as Long.
     */
    public void setSize( final Long size ){
        this.size = size;
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
