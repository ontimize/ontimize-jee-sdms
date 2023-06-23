package com.ontimize.jee.sdms.engine.s3.util.input.filter.reader;

import com.ontimize.jee.sdms.common.crypter.IOSdmsCrypter;
import com.ontimize.jee.sdms.common.path.builder.IOSdmsPathBuilder;
import com.ontimize.jee.sdms.common.path.validator.IOSdmsPathValidator;
import com.ontimize.jee.sdms.common.workspace.OSdmsWorkspace;
import com.ontimize.jee.sdms.common.workspace.manager.IOSdmsWorkspaceManager;
import com.ontimize.jee.sdms.engine.s3.util.input.filter.OSdmsS3InputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Implementation of {@link IOSdmsS3FilterReader} interface.
 *
 * @see IOSdmsS3FilterReader
 */
@Component( "OSdmsS3FilterReader" )
public class OSdmsS3FilterReader implements IOSdmsS3FilterReader {

    /** The workspace manager. */
    private @Autowired IOSdmsWorkspaceManager workspaceManager;

    /** The path builder. */
    private @Autowired IOSdmsPathBuilder pathBuilder;

    /** The path validator. */
    private @Autowired IOSdmsPathValidator pathValidator;

    /** The Base64 crypter. */
    @Qualifier( "OSdmsBase64Crypter" )
    private @Autowired IOSdmsCrypter crypter;

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public List<String> readAllKeys( final OSdmsS3InputFilter filter ) {
        if( filter == null ) return Collections.emptyList();

        final Set<String> result = new HashSet<>();
        final OSdmsWorkspace activeWorkspace = this.workspaceManager.getActive();
        final List<String> patterns = activeWorkspace != null ? activeWorkspace.getPatterns() : Collections.emptyList();

        if( filter.hasIds() ) {
            final List<String> id = filter.getIds();
            result.addAll( id.stream().map( this.crypter::decode ).collect( Collectors.toList() ) );
        }

        if( filter.hasKeys() ) {
            result.addAll( filter.getKeys() );
        }

        final List<String> paths = new ArrayList<>();
        if( activeWorkspace != null ) {
            paths.addAll( activeWorkspace.getPatterns() );
        }

        final List<String> prefixes = new ArrayList<>();
        if( filter.hasPrefixes() ) {
            prefixes.addAll( filter.getPrefixes() );
        }

        final List<String> fileNames = new ArrayList<>();
        if( filter.hasFileNames() ) {
            fileNames.addAll( filter.getFileNames() );
        }

        if( ! prefixes.isEmpty() || ! fileNames.isEmpty() ) {
            result.addAll( this.pathBuilder.buildKeyList( patterns, prefixes, fileNames ) );
        }

        return result.stream()
                .filter( key -> this.pathValidator.validate( key, patterns ) )
                .collect( Collectors.toList() );
    }


    @Override
    public String readKey( final OSdmsS3InputFilter filter ) {
        if( filter == null ) return null;

        String result = null;

        if( filter.hasIds() ) {
            final List<String> ids = filter.getIds();
            if( ids.size() == 1 ) {
                result = this.crypter.decode( ids.get( 0 ) );
            }
        }

        if( result == null && filter.hasKeys() ) {
            final List<String> keys = filter.getKeys();
            if( keys.size() == 1 ) {
                result = keys.get( 0 );
            }
        }

        if( result == null ) {
            final OSdmsWorkspace activeWorkspace = this.workspaceManager.getActive();
            final List<String> workspaces = new ArrayList<>();
            if( activeWorkspace != null ) {
                workspaces.addAll( activeWorkspace.getPatterns() );
            }

            final List<String> prefixes = new ArrayList<>();
            if( filter.hasPrefixes() ) {
                prefixes.addAll( filter.getPrefixes() );
            }

            final List<String> fileNames = new ArrayList<>();
            if( filter.hasFileNames() ) {
                fileNames.addAll( filter.getFileNames() );
            }

            final List<String> paths = this.pathBuilder.buildKeyList( workspaces, prefixes, fileNames );
            result = paths != null && paths.size() == 1 ? paths.get( 0 ) : null;
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
