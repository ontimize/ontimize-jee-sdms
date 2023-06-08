package com.ontimize.jee.sdms.common.zip;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;




/**
 * The OSdmsZipCompressor class is an implementation of the {@link IOSdmsZipCompressor} interface, which provides
 * functionality to compress lists of {@link IOSdmsZippeable} elements into a ZIP file.
 */
@Component( "OSdmsZipCompressor" )
public class OSdmsZipCompressor implements IOSdmsZipCompressor {

    /**
     * The LOGGER constant, which is an instance of org.slf4j.Logger used for logging events and diagnostic messages
     * during program execution.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsZipCompressor.class );

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsZipCompressor(){}

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public <T extends IOSdmsZippeable> OSdmsZipDto compress( final String zipName, final List<T> dataToZip ) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ZipOutputStream zos = new ZipOutputStream(baos);
        final Set<OSdmsZipData> data = dataToZip.stream()
                .map( target -> target.getDataToZip() )
                .filter( target -> target != null )
                .collect( Collectors.toSet() );

        for( final OSdmsZipData zipData : data ) {
            final ZipEntry entry = new ZipEntry( zipData.getFileName() );
            try {
                zos.putNextEntry(entry);
                final byte[] bytes = new byte[1024];
                int length;
                while( (length = zipData.getInputStream().read( bytes )) >= 0 ) {
                    zos.write( bytes, 0, length );
                }
            }
            catch ( final IOException e ) {
                LOGGER.error( "Error compressing data to ZIP file: {}", e.getMessage() );
            }
            finally {
                try {
                    zos.closeEntry();
                    zipData.getInputStream().close();
                }
                catch ( final IOException e) {
                    LOGGER.error( "Error closing Resoources: {}", e.getMessage() );
                }
            }
        }

        try {
            zos.close();
        }
        catch ( final IOException e) {
            LOGGER.error( "Error closing ZIP file: {}", e.getMessage());
        }

        final ByteArrayInputStream bais = new ByteArrayInputStream( baos.toByteArray() );
        final OSdmsZipDto zipDto = new OSdmsZipDto();
        zipDto.setFile(bais);
        zipDto.setName(zipName);
        zipDto.setSize( baos.size() );

        return zipDto;
    }


// ------------------------------------------------------------------------------------------------------------------ \\

}
