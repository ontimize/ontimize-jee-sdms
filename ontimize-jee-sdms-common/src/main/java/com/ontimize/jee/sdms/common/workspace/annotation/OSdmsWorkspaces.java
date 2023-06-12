package com.ontimize.jee.sdms.common.workspace.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * Annotation to define in a class or method the workspaces to use.
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( {ElementType.TYPE, ElementType.METHOD })
public @interface OSdmsWorkspaces {

    /** The workspaces annotations. */
    OSdmsWorkspace[] value();
}
