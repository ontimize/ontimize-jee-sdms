package com.ontimize.jee.sdms.common.workspace.autoregister;

import com.ontimize.jee.sdms.common.workspace.annotation.OSdmsWorkspace;
import com.ontimize.jee.sdms.common.workspace.manager.IOSdmsWorkspaceManager;
import com.ontimize.jee.sdms.common.workspace.annotation.OSdmsWorkspaces;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;



/**
 * Implementation of {@link IOSdmsWorkspaceManagerAutoRegister} that registers the workspaces from annotations.
 *
 * @see OSdmsWorkspace
 */
@Component( "OSdmsWorkspaceManagerAutoRegister" )
public class OSdmsWorkspaceManagerAutoRegister implements IOSdmsWorkspaceManagerAutoRegister {

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsWorkspaceManagerAutoRegister(){}

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void run( final IOSdmsWorkspaceManager factory ) {
        final Map<String, String> data = new HashMap<>();

        try{
            data.putAll( this.getWorkspaceDataFromAnnotationsFromCallerElement() );
        }
        catch ( final ClassNotFoundException e ) {
            e.printStackTrace();
        }

        data.forEach( ( key, value ) -> factory.register( key, value ) );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| UTILITIES |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Method that returns the workspace data if the element that called this method has the {@link OSdmsWorkspace} annotation.
     *
     * @return The workspace data if the element that called this method has the {@link OSdmsWorkspace} annotation.
     *
     * @throws ClassNotFoundException
     */
    private Map<String, String> getWorkspaceDataFromAnnotationsFromCallerElement() throws ClassNotFoundException {
        final Map<String, String> result = new HashMap<>();

        //Get StackTrace
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for( int i = 0 ; i < stackTrace.length && result.isEmpty() ; i++ ){
            final StackTraceElement stackTraceElement = stackTrace[ i ];
            final String className = stackTraceElement.getClassName();
            final Class<?> clazz = Class.forName( className );
            if( clazz.isAnnotationPresent( OSdmsWorkspace.class ) ){
                result.putAll( this.getWorspaceDataIfElementHasOSdmsWorkspacesAnnotation( clazz ) );
                final String callerMethodName = stackTraceElement.getMethodName();
                final Method[] methods = clazz.getDeclaredMethods();
                final Method callerMethod = this.findCallerMethod( callerMethodName, methods );
                result.putAll( this.getWorspaceDataIfElementHasOSdmsWorkspacesAnnotation( callerMethod ) );
            }
        }

        return result;
    }


    /**
     * Method that returns the caller method if it has the {@link OSdmsWorkspace} annotation.
     *
     * @param callerMethodName The name of the caller method.
     * @param methods The methods of the class.
     *
     * @return The caller method if it has the {@link OSdmsWorkspace} annotation.
     */
    private Method findCallerMethod( final String callerMethodName, final Method[] methods ){
        Method result = null;
        for( int i = 0 ; i < methods.length && result == null ; i++ ){
            final Method method = methods[ i ];
            if( method.getName().equals( callerMethodName ) ){
                result = method;
            }
        }
        return result;
    }


    /**
     * Method that returns the workspace data if the element has the {@link OSdmsWorkspace} annotation.
     *
     * @param annotatedElement The element to check.
     *
     * @return The workspace data if the element has the {@link OSdmsWorkspace} annotation.
     */
    private Map<String, String> getWorspaceDataIfElementHasOSdmsWorkspacesAnnotation( final AnnotatedElement annotatedElement ){
        final Map<String, String> result = new HashMap<>();
        if( annotatedElement == null ) return result;

        if( annotatedElement.isAnnotationPresent( OSdmsWorkspace.class )){
            final OSdmsWorkspace annotation = annotatedElement.getAnnotation( OSdmsWorkspace.class );
            result.put( annotation.name(), annotation.value() );result.put( annotation.name(), annotation.value() );
        }

        if( annotatedElement.isAnnotationPresent( OSdmsWorkspaces.class )){
            final OSdmsWorkspaces annotationWorkspacesArray = annotatedElement.getAnnotation( OSdmsWorkspaces.class );
            final OSdmsWorkspace[] annotations = annotationWorkspacesArray.value();

            for( final OSdmsWorkspace annotation : annotations ){
                result.put( annotation.name(), annotation.value() );
            }
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
