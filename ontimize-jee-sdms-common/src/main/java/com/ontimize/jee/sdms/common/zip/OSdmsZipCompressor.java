package com.ontimize.jee.sdms.common.zip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public <T extends IOSdmsZippeable> OSdmsZipDto compress( final String zipName, final List<T> dataToZip ) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            final Set<OSdmsZipData> data = dataToZip.stream()
                    .map(IOSdmsZippeable::getDataToZip)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            for (final OSdmsZipData zipData : data) {
                final ZipEntry entry = new ZipEntry(zipData.getFileName());
                try ( InputStream inputStream = new ByteArrayInputStream( zipData.getFileContent() )) {
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) >= 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                } catch (IOException e) {
                    LOGGER.error("Error compressing entry {}: {}", zipData.getFileName(), e.getMessage());
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error creating ZIP output stream: {}", e.getMessage());
        }

        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        final OSdmsZipDto zipDto = new OSdmsZipDto();
        zipDto.setFile(bais);
        zipDto.setName(zipName);
        zipDto.setSize(baos.size());

        return zipDto;
    }


// ------------------------------------------------------------------------------------------------------------------ \\

}
