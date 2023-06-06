package com.ontimize.jee.sdms.common.workspace.manager;

import com.ontimize.jee.sdms.common.path.builder.IOSdmsPathBuilder;
import com.ontimize.jee.sdms.common.workspace.OSdmsWorkspace;
import com.ontimize.jee.sdms.common.workspace.autoregister.IOSdmsWorkspaceManagerAutoRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;



/**
 * Implementation of the {@link IOSdmsWorkspaceManager} interface.
 *
 * @see IOSdmsWorkspaceManager
 */
@Component( "OSdmsWorkspaceManager" )
@Scope( value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS )
public class OSdmsWorkspaceManager implements IOSdmsWorkspaceManager {

    /** The workspaces registered. */
    private Set<OSdmsWorkspace> workspaces = new HashSet<>( Set.of( new OSdmsWorkspace() ));

    /** The default workspace. */
    private OSdmsWorkspace workspaceDefault = null;

    /** The active workspace. */
    private OSdmsWorkspace active;

    /** The flag that indicates if the auto register is enable. */
    private boolean isAutoRegisterEnable = true;

    /** The workspace manager auto register. */
    private @Autowired IOSdmsWorkspaceManagerAutoRegister workspaceManagerAutoRegister;

    /** The path builder. */
    private @Autowired IOSdmsPathBuilder pathBuilder;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsWorkspaceManager(){}

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void register( final String name, final String value ) {
        this.autoRegister();
        if( this.exists( name ) ){
            this.update( name, value );
        }
        else{
            this.workspaces.add( new OSdmsWorkspace( name, value, Collections.emptyList()));
        }
    }



    @Override
    public void register( final OSdmsWorkspace workspace ) {
        this.register( workspace.getName(), workspace.getValue() );
    }



    @Override
    public void update( final String name, final String value ) {
        this.autoRegister();
        this.workspaces.stream()
                .filter( target -> target.getName().equals( name ))
                .findFirst()
                .ifPresent( target -> {
                    target.setValue( value );
                    target.setPatterns(Collections.emptyList());
                });
    }



    @Override
    public void update( final OSdmsWorkspace workspace ) {
        this.update( workspace.getName(), workspace.getValue() );
    }



    @Override
    public void unregister( final String name ) {
        this.autoRegister();
        this.workspaces.removeIf( target -> target.getName().equals( name ));
    }



    @Override
    public void unregister( final OSdmsWorkspace workspace ) {
        this.unregister( workspace.getName() );
    }



    @Override
    public OSdmsWorkspace get(final String name ) {
        this.autoRegister();
        OSdmsWorkspace result = null;
        final Optional<OSdmsWorkspace> optional = this.workspaces.stream().filter(target -> target.getName().equals( name )).findFirst();
        if( optional.isPresent() ){
            OSdmsWorkspace target = optional.get();
            final List<String> paths = List.copyOf( target.getPatterns() );
            result = new OSdmsWorkspace( target.getName(), target.getValue(), paths );
        }
        return result;
    }



    @Override
    public OSdmsWorkspace getDefault() {
        this.autoRegister();
        OSdmsWorkspace result = null;
        if( this.workspaceDefault == null ) this.workspaceDefault = this.get( OSdmsWorkspace.DEFAULT );
        if( this.workspaceDefault != null ){
            final List<String> paths = List.copyOf( this.workspaceDefault.getPatterns() );
            result = new OSdmsWorkspace( this.workspaceDefault.getName(), this.workspaceDefault.getValue(), paths );
        }
        return result;
    }



    @Override
    public OSdmsWorkspace getActive() {
        this.autoRegister();
        OSdmsWorkspace result = null;
        if( this.active == null ) this.active = this.getDefault();
        if( this.active != null ){
            final List<String> paths = List.copyOf( this.active.getPatterns() );
            result = new OSdmsWorkspace( this.active.getName(), this.active.getValue(), paths );
        }
        return result;
    }



    @Override
    public void activeDefault() {
        this.activeDefault( null );
    }



    @Override
    public void activeDefault( final Map<String, Object> data ) {
        this.active( OSdmsWorkspace.DEFAULT, data );
    }



    @Override
    public void active( final String name, final Map<String, Object> data ) {
        this.autoRegister();
        if( OSdmsWorkspace.DEFAULT.equals( name ) || name == null || !this.exists( name ) ){
            this.active = this.getDefault();
        }
        else{
            this.active = this.get( name );
        }
        final List<String> paths = this.pathBuilder.buildKeyListFromPattern( this.active.getValue(), data );
        this.active.setPatterns( paths );
    }



    @Override
    public void active( final String name ) {
        this.active( name, null );
    }



    @Override
    public void active( final OSdmsWorkspace workspace ) {
        this.active( workspace.getName() );
    }



    @Override
    public void active(final OSdmsWorkspace workspace, final Map<String, Object> data ) {
        this.active( workspace.getName(), data );
    }



    @Override
    public List<OSdmsWorkspace> list() {
        this.autoRegister();
        return new ArrayList<>( this.workspaces );
    }



    @Override
    public boolean exists( final String name ) {
        this.autoRegister();
        return this.workspaces.stream().anyMatch( target -> target.getName().equals( name ));
    }



    @Override
    public boolean exists( final OSdmsWorkspace workspace ) {
        return this.exists( workspace.getName() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| UTILITIES |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Method that registers the workspaces annotated with the {@link OSdmsWorkspace} annotation.
     */
    private void autoRegister() {
        if( this.isAutoRegisterEnable ){
            this.isAutoRegisterEnable = false;
            this.workspaceManagerAutoRegister.run( this );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
