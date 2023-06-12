package com.ontimize.jee.sdms.common.workspace;

import java.util.ArrayList;
import java.util.List;



/**
 * Class that represents a workspace.
 */
public class OSdmsWorkspace {

    public static final String DEFAULT = "default";

    /** The name of the workspace. */
    private String name = OSdmsWorkspace.DEFAULT;

    /** The value of the workspace. */
    private String value;

    /** The patterns of the workspace. */
    private List<String> patterns = new ArrayList<>();

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsWorkspace(){}

    public OSdmsWorkspace( final String name, final String value, final List<String> patterns ) {
        this.setName( name );
        this.setValue( value );
        this.setPatterns( patterns );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| GETTERS AND SETTERS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public String getName() {
        return this.name;
    }

    public void setName( final String name ) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue( final String value ) {
        this.value = value;
    }

    public List<String> getPatterns() {
        return this.patterns;
    }

    public void setPatterns( final List<String> patterns ) {
        this.patterns = patterns;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| TO STRING ||---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder( "OSdmsWorkspace{" );
        sb.append( "name='" ).append( this.name ).append( '\'' );
        sb.append( ", value='" ).append( this.value ).append( '\'' );
        sb.append( ", patterns=" ).append( this.patterns );
        sb.append( '}' );
        return sb.toString();
    }

// ------------------------------------------------------------------------------------------------------------------ \\
}
