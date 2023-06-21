package com.ontimize.jee.sdms.common.workspace.manager;


import com.ontimize.jee.sdms.common.workspace.OSdmsWorkspace;

import java.util.List;
import java.util.Map;


/**
 * Interface that defines the methods to manage the workspaces.
 */
public interface IOSdmsWorkspaceManager {

    /**
     * Registers a workspace from name and value.
     *
     * @param name  The given name to register the workspace.
     * @param value The given value to register the workspace.
     */
    void register( String name, String value );


    /**
     * Registers a workspace.
     *
     * @param workspace The given workspace to register.
     */
    void register( OSdmsWorkspace workspace );


    /**
     * Unregisters a workspace from name.
     *
     * @param name The given name to unregister the workspace.
     */
    void unregister( String name );


    /**
     * Unregisters a workspace.
     *
     * @param workspace The given workspace to unregister.
     */
    void unregister( OSdmsWorkspace workspace );


    /**
     * Updates a workspace from name and value.
     *
     * @param name  The given name to update the workspace.
     * @param value The given value to update the workspace.
     */
    void update( String name, String value );


    /**
     * Updates a workspace.
     *
     * @param workspace The given workspace to update.
     */
    void update( OSdmsWorkspace workspace );


    /**
     * Gets a workspace from name.
     *
     * @param name The given name to get the workspace.
     *
     * @return The workspace.
     */
    OSdmsWorkspace get( String name );


    /**
     * Gets the default workspace.
     *
     * @return The default workspace.
     */
    OSdmsWorkspace getDefault();


    /**
     * Gets the active workspace.
     *
     * @return The active workspace.
     */
    OSdmsWorkspace getActive();


    /**
     * Activates the default workspace.
     */
    void activeDefault();


    /**
     * Activates the default workspace with the given data.
     *
     * @param data The given data to build the workspace patterns.
     */
    void activeDefault( Map<String, Object> data );


    /**
     * Activates a workspace from name.
     *
     * @param name The given name to activate the workspace.
     */
    void active( String name );


    /**
     * Activates a workspace from name with data.
     *
     * @param name The given name to activate the workspace.
     * @param data The given data to build the workspace patterns.
     */
    void active( String name, Map<String, Object> data );


    /**
     * Activates a workspace.
     *
     * @param workspace The given workspace to activate.
     */
    void active( OSdmsWorkspace workspace );


    /**
     * Activates a workspace with data.
     *
     * @param workspace The given workspace to activate.
     * @param data      The given data to build the workspace patterns.
     */
    void active( OSdmsWorkspace workspace, Map<String, Object> data );


    /**
     * Obtains a list of workspaces.
     *
     * @return The list of workspaces.
     */
    List<OSdmsWorkspace> list();


    /**
     * Checks if a workspace exists from name.
     *
     * @param name The given name to check the workspace.
     *
     * @return True if the workspace exists, false otherwise.
     */
    boolean exists( String name );


    /**
     * Checks if a workspace exists.
     *
     * @param workspace The given workspace to check.
     *
     * @return True if the workspace exists, false otherwise.
     */
    boolean exists( OSdmsWorkspace workspace );

}
