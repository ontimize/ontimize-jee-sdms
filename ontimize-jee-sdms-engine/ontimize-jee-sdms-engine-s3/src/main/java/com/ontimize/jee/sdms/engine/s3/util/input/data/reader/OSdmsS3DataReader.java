package com.ontimize.jee.sdms.engine.s3.util.input.data.reader;

import com.ontimize.jee.sdms.common.crypter.IOSdmsCrypter;
import com.ontimize.jee.sdms.common.path.builder.IOSdmsPathBuilder;
import com.ontimize.jee.sdms.common.path.validator.IOSdmsPathValidator;
import com.ontimize.jee.sdms.common.workspace.OSdmsWorkspace;
import com.ontimize.jee.sdms.common.workspace.manager.IOSdmsWorkspaceManager;
import com.ontimize.jee.sdms.engine.s3.util.input.data.OSdmsS3InputData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * Implementation of {@link IOSdmsS3DataReader} interface.
 *
 * @see IOSdmsS3DataReader
 */
@Component( "OSdmsS3DataReader" )
public class OSdmsS3DataReader implements IOSdmsS3DataReader {

    /**
     * The LOGGER constant, which is an instance of org.slf4j.Logger used for logging events and diagnostic messages
     * during program execution.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsS3DataReader.class );

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
    public String readKey( final OSdmsS3InputData data ) {
        if( data == null ) return null;

        String result = null;

        if( result == null && data.hasId() ) {
            result = this.crypter.decode( data.getId() );
        }

        if( result == null && data.hasKey() ) {
            result = data.getKey();
        }

        if( result == null ) {
            String prefix = null;
            if( data.hasPrefix() ) {
                prefix = data.getPrefix();
            }

            String fileName = null;
            if( data.hasFileName() ) {
                fileName = data.getFileName();
            }

            if( prefix != null && fileName != null ) {
                final OSdmsWorkspace activeWorkspace = this.workspaceManager.getActive();
                String workspace = null;
                if( activeWorkspace != null ) {
                    workspace = ! activeWorkspace.getPatterns().isEmpty() ? activeWorkspace.getPatterns().get(
                            0 ) : workspace;
                }

                result = this.pathBuilder.buildKey( workspace, prefix, fileName );
            }
        }

        return result;
    }


    @Override
    public String readPrefix( final OSdmsS3InputData data ) {
        if( data == null ) return null;

        String prefix = null;
        if( data.hasPrefix() ) {
            prefix = data.getPrefix();
        }

        final OSdmsWorkspace activeWorkspace = this.workspaceManager.getActive();
        String workspace = null;
        if( prefix != null && activeWorkspace != null ) {
            workspace = ! activeWorkspace.getPatterns().isEmpty() ? activeWorkspace.getPatterns().get( 0 ) : workspace;
        }

        String result = this.pathBuilder.buildKey( workspace, prefix, ( String ) null );
        if( result != null && ! result.endsWith( "/" ) ) result = result.concat( "/" );

        return result;
    }


    @Override
    public String readCurrentPrefix( final OSdmsS3InputData data ) {
        if( data == null ) return null;

        final OSdmsWorkspace activeWorkspace = this.workspaceManager.getActive();

        String currentPrefix = null;
        if( data.hasCurrentPrefix() ) {
            currentPrefix = data.getCurrentPrefix();
        }

        if( currentPrefix != null && currentPrefix.equals( "/" ) && activeWorkspace != null ) {
            currentPrefix = ! activeWorkspace.getPatterns().isEmpty() ? activeWorkspace.getPatterns().get(
                    0 ) : currentPrefix;
        }

        if( currentPrefix != null && ! currentPrefix.endsWith( "/" ) ) currentPrefix = currentPrefix.concat( "/" );

        return currentPrefix;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
