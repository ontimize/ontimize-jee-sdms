package com.ontimize.jee.sdms.common.crypter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;



/**
 * The OBase64Crypter class provides methods to encode and decode data using the Base64 encryption algorithm.
 *
 * @see Base64
 */
@Component( "OSdmsBase64Crypter" )
public class OSdmsBase64Crypter implements IOSdmsCrypter {

    /**
     * The LOGGER constant, which is an instance of org.slf4j.Logger used for logging events and diagnostic messages
     * during program execution.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsBase64Crypter.class );

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsBase64Crypter(){}

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Encodes the specified string data using the Base64 encryption algorithm.
     *
     * @param data The string data to encode.
     *
     * @return The encoded string.
     */
    @Override
    public String encode( final String data ){
        final String dataToEncrypt = data != null ? data : "";
        return new String( Base64.getEncoder().encode( dataToEncrypt.getBytes( StandardCharsets.UTF_8 )));
    }


    /**
     * Decodes the specified encoded string using the Base64 encryption algorithm.
     *
     * @param data The encoded string to decode.
     *
     * @return The decoded string.
     */
    @Override
    public String decode( final String data ){
        String result = null;
        if( data != null ) {
            result = new String(Base64.getDecoder().decode( data ));
        }
        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
