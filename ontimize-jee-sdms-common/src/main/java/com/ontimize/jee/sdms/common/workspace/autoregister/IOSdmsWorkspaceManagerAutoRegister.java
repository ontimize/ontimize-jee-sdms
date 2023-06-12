package com.ontimize.jee.sdms.common.workspace.autoregister;


import com.ontimize.jee.sdms.common.workspace.manager.IOSdmsWorkspaceManager;

/**
 * Interface that defines the methods to auto register the workspaces.
 */
public interface IOSdmsWorkspaceManagerAutoRegister {

    /**
     * Runs the auto register.
     *
     * @param wokspaceManager The given workspace manager to register the workspaces.
     */
    void run( IOSdmsWorkspaceManager wokspaceManager );

}
