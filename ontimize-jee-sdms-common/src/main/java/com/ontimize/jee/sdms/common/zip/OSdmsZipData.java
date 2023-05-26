package com.ontimize.jee.sdms.common.zip;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.Objects;



/**
 * This class represents the data that will be compressed by the {@link IOSdmsZipCompressor}.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
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

    @Override
    public boolean equals( final Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null || getClass() != obj.getClass()) return false;
        final OSdmsZipData target = (OSdmsZipData) obj;
        return Objects.equals( this.fileName, target.fileName );
    }

    @Override
    public int hashCode() {
        return Objects.hash( this.fileName );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
}
