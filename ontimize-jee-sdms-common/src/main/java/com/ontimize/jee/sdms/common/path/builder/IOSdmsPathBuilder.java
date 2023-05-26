package com.ontimize.jee.sdms.common.path.builder;

import java.util.List;
import java.util.Map;



/**
 * Interface that defines the methods to build a S3 key.
 *
 * @see IOSdmsPathBuilder
 */
public interface IOSdmsPathBuilder {

    /**
     * Builds the key from pattern and data to replace all variables in pattern.
     *
     * @param pattern The given pattern to build the key from data.
     * @param data The given data to replace variables in pattern.
     *
     * @return The built key.
     */
    List<String> buildKeyListFromPattern(String pattern, Map<String, Object> data );


    /**
     * Builds the key from pattern list and key to replace all variables in patterns by values.
     *
     * @param patterns The given pattern list to build the key from key and values.
     * @param key The given key to replace variables in patterns by values.
     * @param values The given values to replace variables in patterns.
     *
     * @return The built key list.
     */
    List<String> buildKeyListFromPattern(List<String> patterns, String key, List<Object> values );


    /**
     * Builds the key from workspace, prefix and fileName.
     *
     * @param workspace The given workspace to build the key.
     * @param prefix The given prefix to build the key.
     * @param fileName The given fileName to build the key.
     *
     * @return The built key.
     */
    String buildKey(String workspace, String prefix, String fileName );


    /**
     * Builds the key from prefix and fileName.
     *
     * @param prefix The given prefix to build the key.
     * @param fileName The given fileName to build the key.
     *
     * @return The built key.
     */
    String buildKey(String prefix, String fileName );


    /**
     * Builds the key from workspace list, prefix and fileName.
     *
     * @param workspace The given workspace list to build the key.
     * @param prefix The given prefix to build the key.
     * @param fileName The given fileName to build the key.
     *
     * @return The built keys.
     */
    List<String> buildKeyList(List<String> workspace, String prefix, String fileName );


    /**
     * Builds the key from workspace list, prefix list and fileName list.
     *
     * @param workspaces The given workspace list to build the key.
     * @param prefixes The given prefix list to build the key.
     * @param fileNames The given fileName list to build the key.
     *
     * @return The built keys.
     */
    List<String> buildKeyList(List<String> workspaces, List<String> prefixes, List<String> fileNames );


    /**
     * Builds the key from prefix list and fileName list.
     *
     * @param prefixes The given prefix list to build the key.
     * @param fileNames The given fileName list to build the key.
     *
     * @return The built keys.
     */
    List<String> buildKeyList(List<String> prefixes, List<String> fileNames );

}
