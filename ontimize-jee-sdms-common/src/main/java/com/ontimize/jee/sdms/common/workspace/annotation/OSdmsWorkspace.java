package com.ontimize.jee.sdms.common.workspace.annotation;

import java.lang.annotation.*;


/**
 * Annotation to define in a class or method a workspace data.
 */
@Repeatable( OSdmsWorkspaces.class )
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.TYPE, ElementType.METHOD } )
public @interface OSdmsWorkspace {

    /** The name of the workspace. */
    String name() default com.ontimize.jee.sdms.common.workspace.OSdmsWorkspace.DEFAULT_NAME;

    /** The value of the workspace. */
    String value();

}
