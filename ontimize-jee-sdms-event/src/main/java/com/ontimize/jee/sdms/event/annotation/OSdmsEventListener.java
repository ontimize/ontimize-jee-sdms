package com.ontimize.jee.sdms.event.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation marks a class as an Ontimize Event Listener. An Ontimize Event Listener is a class that implements
 * the {@link IOSdmsEventListener} interface and is responsible for handling Ontimize events.
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface OSdmsEventListener {
}
