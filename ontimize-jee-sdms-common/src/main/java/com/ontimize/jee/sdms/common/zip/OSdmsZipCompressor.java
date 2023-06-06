package com.ontimize.jee.sdms.common.zip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
    public <T extends IOSdmsZippeable> OSdmsZipDto compress(final String zipName, final List<T> dataToZip ){
        //Initialize result as null
        OSdmsZipDto result = null;

        //Initialize data
        byte[] buffer = new byte[ 1024 ];
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ZipOutputStream zos = new ZipOutputStream(baos);
        final Set<OSdmsZipData> data = dataToZip.stream()
                .map( target -> target.getDataToZip() )
                .filter( target -> target != null )
                .collect( Collectors.toSet() );

        try {
            //Iterate data
            for( final OSdmsZipData oSdmsZipData : data ){

                //Check data
                if( oSdmsZipData != null ) { //If exists data
                    //Get name and bytes from data
                    final String fileName = oSdmsZipData.getFileName();
                    final InputStream inputStream = oSdmsZipData.getInputStream();

                    //Add entry to zip
                    zos.putNextEntry(new ZipEntry(fileName));

                    //Read File
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        //Write Zip
                        zos.write(buffer, 0, length);
                    }

                    //Close resources
                    zos.closeEntry();
                    inputStream.close();
                }
            }

            //Check baos size
            if( baos.size() > 0 ) { //If exists data
                //Add data to result
                result = new OSdmsZipDto();
                result.setFile(new ByteArrayInputStream(baos.toByteArray()));
                result.setName(zipName);
                result.setSize(baos.size());
            }

            //Close ZipOutputStream resource
            zos.close();
        }
        catch( final IOException exception ){
            LOGGER.error( exception.getMessage() );
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
