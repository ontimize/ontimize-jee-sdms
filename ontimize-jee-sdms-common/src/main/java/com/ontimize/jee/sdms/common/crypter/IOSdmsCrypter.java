package com.ontimize.jee.sdms.common.crypter;



/**
 * The IOCrypter interface provides methods to encode and decode data using a encryption algorithm.
 */
public interface IOSdmsCrypter {

    /**
     * Encodes the specified string data using a encryption algorithm.
     *
     * @param data The string data to encode.
     *
     * @return The encoded string.
     */
    String encode( String data );


    /**
     * Decodes the specified encoded string using the same encryption algorithm.
     *
     * @param data The encoded string to decode.
     *
     * @return The decoded string.
     */
    String decode( String data );

}
