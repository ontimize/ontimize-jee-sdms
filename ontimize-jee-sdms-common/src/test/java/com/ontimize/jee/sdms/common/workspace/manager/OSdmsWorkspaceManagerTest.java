package com.ontimize.jee.sdms.common.workspace.manager;

import com.ontimize.jee.sdms.common.path.builder.IOSdmsPathBuilder;
import com.ontimize.jee.sdms.common.workspace.OSdmsWorkspace;
import com.ontimize.jee.sdms.common.workspace.autoregister.IOSdmsWorkspaceManagerAutoRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith( SpringExtension.class )
public class OSdmsWorkspaceManagerTest {
    private @Spy IOSdmsWorkspaceManagerAutoRegister workspaceManagerAutoRegister;
    private @Mock IOSdmsPathBuilder pathBuilder;
    private @InjectMocks IOSdmsWorkspaceManager workspaceManager = new OSdmsWorkspaceManager();

    @BeforeEach
    void setUp() {
        //when( this.pathBuilder.buildKeyListFromPattern( anyString(), anyMap() ) ).thenReturn( Collections.emptyList() );
        doAnswer( invocation -> {
            this.workspaceManager.register( OSdmsWorkspace.DEFAULT_NAME, OSdmsWorkspace.DEFAULT_VALUE );
            return null;
        } ).when( this.workspaceManagerAutoRegister ).run( this.workspaceManager );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| REGISTER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Test
    public void givenNameAndValueOfWorkspaceThatNotExists_whenRegister_thenCheckIfWorkspaceIsRegistered(){
        //Given
        final OSdmsWorkspace givenWorkspace = new OSdmsWorkspace( "name", "value", Collections.emptyList() );

        //When
        this.workspaceManager.register( givenWorkspace.getName(), givenWorkspace.getValue() );

        //Then
        final List<OSdmsWorkspace> workspaces = this.workspaceManager.list();
        assertNotNull( workspaces, () -> "The list of workspaces should not be null" );
        assertFalse( workspaces.isEmpty(), () -> "The list of workspaces should not be empty" );
        assertEquals( 2, workspaces.size(), () -> "The list of workspaces should have two elements" );

        final OSdmsWorkspace registeredWorkspace = workspaces.stream()
                .filter( workspace -> workspace.getName().equals( givenWorkspace.getName() ) )
                .findFirst().orElse( null );
        assertNotNull( registeredWorkspace, () -> "The registered workspace should not be null" );
        assertEquals( givenWorkspace.getValue(), registeredWorkspace.getValue(), () -> "The registered workspace value should be the same as the given workspace value" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

    @Test
    public void givenNameOfWorkspaceThatAlreadyExistsAndValueThatNotExists_whenRegister_thenCheckIfWorkspaceValueHasBeenUpdated(){
        //Given
        final OSdmsWorkspace givenWorkspace = new OSdmsWorkspace( "name", "value", Collections.emptyList() );
        final String givenValueThatNotExists = "value-not-exists";

        //When
        this.workspaceManager.register( givenWorkspace );
        this.workspaceManager.register( givenWorkspace.getName(), givenValueThatNotExists );

        //Then
        final List<OSdmsWorkspace> workspaces = this.workspaceManager.list();
        assertNotNull( workspaces, () -> "The list of workspaces should not be null" );
        assertFalse( workspaces.isEmpty(), () -> "The list of workspaces should not be empty" );
        assertEquals( 2, workspaces.size(), () -> "The list of workspaces should have two elements" );

        final OSdmsWorkspace registeredWorkspace = workspaces.stream()
                .filter( workspace -> workspace.getName().equals( givenWorkspace.getName() ) )
                .findFirst().orElse( null );
        assertNotNull( registeredWorkspace, () -> "The registered workspace should not be null" );
        assertEquals( givenValueThatNotExists, registeredWorkspace.getValue(), () -> "The registered workspace value should be the same as the given workspace value" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );

    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| UNREGISTER |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Test
    public void givenWorkspaceRegistered_whenUnregisterFromName_thenCheckIfWorkspaceIsUnregistered(){
        //Given
        final OSdmsWorkspace givenWorkspace = new OSdmsWorkspace( "name", "value", Collections.emptyList() );

        //Register
        this.workspaceManager.register( givenWorkspace.getName(), givenWorkspace.getValue() );
        List<OSdmsWorkspace> workspaces = this.workspaceManager.list();
        assertNotNull( workspaces, () -> "The list of workspaces should not be null" );
        assertFalse( workspaces.isEmpty(), () -> "The list of workspaces should not be empty" );
        assertEquals( 2, workspaces.size(), () -> "The list of workspaces should have two elements" );

        //When
        this.workspaceManager.unregister( givenWorkspace.getName() );

        //Then
        workspaces = this.workspaceManager.list();
        assertNotNull( workspaces, () -> "The list of workspaces should not be null" );
        assertEquals( 1, workspaces.size(), () -> "The list of workspaces should have one element" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

    @Test
    public void givenWorkspaceRegistered_whenUnregister_thenCheckIfWorkspaceIsUnregistered(){
        //Given
        final OSdmsWorkspace givenWorkspace = new OSdmsWorkspace( "name", "value", Collections.emptyList() );

        //Register
        this.workspaceManager.register( givenWorkspace.getName(), givenWorkspace.getValue() );
        List<OSdmsWorkspace> workspaces = this.workspaceManager.list();
        assertNotNull( workspaces, () -> "The list of workspaces should not be null" );
        assertFalse( workspaces.isEmpty(), () -> "The list of workspaces should not be empty" );
        assertEquals( 2, workspaces.size(), () -> "The list of workspaces should have two elements" );

        //When
        this.workspaceManager.unregister( givenWorkspace );

        //Then
        workspaces = this.workspaceManager.list();
        assertNotNull( workspaces, () -> "The list of workspaces should not be null" );
        assertEquals( 1, workspaces.size(), () -> "The list of workspaces should have one element" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| UPDATE |-------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Test
    public void givenWorkspaceThatExists_whenUpdate_thenCheckIfWorkspaceIsUpadted(){
        //Given
        final OSdmsWorkspace givenWorkspace1 = new OSdmsWorkspace( "name", "value", Collections.emptyList() );
        final OSdmsWorkspace givenWorkspace2 = new OSdmsWorkspace( "name", "new-value", Collections.emptyList() );

        //When
        this.workspaceManager.register( givenWorkspace1 );
        this.workspaceManager.update( givenWorkspace2 );

        //Then
        final List<OSdmsWorkspace> workspaces = this.workspaceManager.list();
        assertNotNull( workspaces, () -> "The list of workspaces should not be null" );
        assertFalse( workspaces.isEmpty(), () -> "The list of workspaces should not be empty" );
        assertEquals( 2, workspaces.size(), () -> "The list of workspaces should have two elements" );

        final OSdmsWorkspace registeredWorkspace = workspaces.stream()
                .filter( workspace -> workspace.getName().equals( givenWorkspace2.getName() ) )
                .findFirst().orElse( null );
        assertNotNull( registeredWorkspace, () -> "The registered workspace should not be null" );
        assertEquals( givenWorkspace2.getValue(), registeredWorkspace.getValue(), () -> "The registered workspace value should be the same as the given workspace value" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );

    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| GET |----------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    //Get Workspace
    @Test
    public void givenWorkspaceThatExists_whenGetByNameString_thenCheckIfWorkspaceIsReturned(){
        //Given
        final OSdmsWorkspace givenWorkspace = new OSdmsWorkspace( "name", "value", Collections.emptyList() );
        this.workspaceManager.register( givenWorkspace );

        //When
        final OSdmsWorkspace result = this.workspaceManager.get( givenWorkspace.getName() );

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenWorkspace.getValue(), result.getValue(), () -> "The result value should be the same as the given workspace value" );

        final List<String> patterns = result.getPatterns();
        assertNotNull( patterns, () -> "The result patterns should not be null" );
        assertTrue( patterns.isEmpty(), () -> "The result patterns should be empty" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

    //Get Default
    @Test
    public void givenWorkspaceDefault_whenGetDefaultFirstTime_thenCheckIfWorkspaceDefaultIsReturned(){
        //When
        final OSdmsWorkspace result = this.workspaceManager.getDefault();

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( OSdmsWorkspace.DEFAULT_NAME, result.getName(), () -> "The result name should be the same as the default workspace name" );
        assertEquals( OSdmsWorkspace.DEFAULT_VALUE, result.getValue(), () -> "The result value should be the same as the default workspace value" );

        final List<String> patterns = result.getPatterns();
        assertNotNull( patterns, () -> "The result patterns should not be null" );
        assertTrue( patterns.isEmpty(), () -> "The result patterns should be empty" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

    @Test
    public void givenWorkspaceDefaultAlreadySelected_whenGetDefaultSecondTime_thenCheckIfWorkspaceDefaultIsReturned(){
        //When
        this.workspaceManager.getDefault();
        final OSdmsWorkspace result = this.workspaceManager.getDefault();

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( OSdmsWorkspace.DEFAULT_NAME, result.getName(), () -> "The result name should be the same as the default workspace name" );
        assertEquals( OSdmsWorkspace.DEFAULT_VALUE, result.getValue(), () -> "The result value should be the same as the default workspace value" );

        final List<String> patterns = result.getPatterns();
        assertNotNull( patterns, () -> "The result patterns should not be null" );
        assertTrue( patterns.isEmpty(), () -> "The result patterns should be empty" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

    //Get Active
    @Test
    public void givenWorkspaceActive_whenGetActiveFirstTime_thenCheckIfWorkspaceDefaultIsReturned(){
        //When
        final OSdmsWorkspace result = this.workspaceManager.getActive();

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( OSdmsWorkspace.DEFAULT_NAME, result.getName(), () -> "The result name should be the same as the default workspace name" );
        assertEquals( OSdmsWorkspace.DEFAULT_VALUE, result.getValue(), () -> "The result value should be the same as the default workspace value" );

        final List<String> patterns = result.getPatterns();
        assertNotNull( patterns, () -> "The result patterns should not be null" );
        assertTrue( patterns.isEmpty(), () -> "The result patterns should be empty" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

    @Test
    public void givenWorkspaceActiveAlreadySelected_whenGetActiveSecondTime_thenCheckIfWorkspaceDefaultIsReturned(){
        //When
        this.workspaceManager.getActive();
        final OSdmsWorkspace result = this.workspaceManager.getDefault();

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( OSdmsWorkspace.DEFAULT_NAME, result.getName(), () -> "The result name should be the same as the default workspace name" );
        assertEquals( OSdmsWorkspace.DEFAULT_VALUE, result.getValue(), () -> "The result value should be the same as the default workspace value" );

        final List<String> patterns = result.getPatterns();
        assertNotNull( patterns, () -> "The result patterns should not be null" );
        assertTrue( patterns.isEmpty(), () -> "The result patterns should be empty" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| ACTIVE |-------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\
    @Test
    public void givenWorkspaceDefault_whenActiveDefault_thenCheckIfWorkspaceDefaultIsActive(){
        //Given
        when( this.pathBuilder.buildKeyListFromPattern( anyString(), anyMap() ) ).thenReturn( List.of( "/1", "/2", "/3" ) );

        //When
        this.workspaceManager.activeDefault();
        final OSdmsWorkspace result = this.workspaceManager.getActive();

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( OSdmsWorkspace.DEFAULT_NAME, result.getName(), () -> "The result name should be the same as the default workspace name" );
        assertEquals( OSdmsWorkspace.DEFAULT_VALUE, result.getValue(), () -> "The result value should be the same as the default workspace value" );

        final List<String> patterns = result.getPatterns();
        assertNotNull( patterns, () -> "The result patterns should not be null" );
        assertEquals( 0, patterns.size(), () -> "The result patterns should be empty" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

    @Test
    public void givenWorkspaceDefault_whenActiveDefaultWithData_thenCheckIfWorkspaceDefaultIsActive(){
        //Given
        final Map<String, Object> givenData = Map.of( "id", List.of( 1, 2, 3));
        when( this.pathBuilder.buildKeyListFromPattern( anyString(), anyMap() ) ).thenReturn( List.of( "/1", "/2", "/3" ) );

        //When
        this.workspaceManager.activeDefault( givenData );
        final OSdmsWorkspace result = this.workspaceManager.getActive();

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( OSdmsWorkspace.DEFAULT_NAME, result.getName(), () -> "The result name should be the same as the default workspace name" );
        assertEquals( OSdmsWorkspace.DEFAULT_VALUE, result.getValue(), () -> "The result value should be the same as the default workspace value" );

        final List<String> patterns = result.getPatterns();
        assertNotNull( patterns, () -> "The result patterns should not be null" );
        assertEquals( 3, patterns.size(), () -> "The result patterns should have 3 elements" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

    //Active no Default
    @Test
    public void givenWorkspace_whenActiveByWorkspaceName_thenCheckIfWorkspaceIsActive(){
        //Given
        final String givenName = "workspace";
        final String givenValue = "/wk/{id}";
        when( this.pathBuilder.buildKeyListFromPattern( anyString(), anyMap() ) ).thenReturn( Collections.emptyList() );
        this.workspaceManager.register( givenName, givenValue );

        //When
        this.workspaceManager.active( givenName );
        final OSdmsWorkspace result = this.workspaceManager.getActive();

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenName, result.getName(), () -> "The result name should be the same as the given workspace name" );
        assertEquals( givenValue, result.getValue(), () -> "The result value should be the same as the given workspace value" );

        final List<String> patterns = result.getPatterns();
        assertNotNull( patterns, () -> "The result patterns should not be null" );
        assertEquals( 0, patterns.size(), () -> "The result patterns should be empty" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

    @Test
    public void givenWorkspace_whenActiveByWorkspaceObject_thenCheckIfWorkspaceIsActive(){
        //Given
        final String givenName = "workspace";
        final String givenValue = "/wk/{id}";
        final OSdmsWorkspace givenWorkspace = new OSdmsWorkspace( givenName, givenValue, Collections.emptyList() );
        when( this.pathBuilder.buildKeyListFromPattern( anyString(), anyMap() ) ).thenReturn( Collections.emptyList() );
        this.workspaceManager.register( givenWorkspace );

        //When
        this.workspaceManager.active( givenWorkspace );
        final OSdmsWorkspace result = this.workspaceManager.getActive();

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenName, result.getName(), () -> "The result name should be the same as the given workspace name" );
        assertEquals( givenValue, result.getValue(), () -> "The result value should be the same as the given workspace value" );

        final List<String> patterns = result.getPatterns();
        assertNotNull( patterns, () -> "The result patterns should not be null" );
        assertEquals( 0, patterns.size(), () -> "The result patterns should be empty" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

    @Test
    public void givenWorkspace_whenActiveByWorkspaceObjectWithData_thenCheckIfWorkspaceIsActive(){
        //Given
        final String givenName = "workspace";
        final String givenValue = "/wk/{id}";
        final Map<String, Object> givenData = Map.of( "id", List.of( 1, 2, 3));
        final OSdmsWorkspace givenWorkspace = new OSdmsWorkspace( givenName, givenValue, Collections.emptyList() );
        when( this.pathBuilder.buildKeyListFromPattern( anyString(), anyMap() ) ).thenReturn( List.of( "/wk/1", "/wk/2", "/wk/3" ) );
        when( this.pathBuilder.buildKeyListFromPattern( anyString(), anyMap() ) ).thenReturn( List.of( "/wk/1", "/wk/2", "/wk/3" ) );
        this.workspaceManager.register( givenWorkspace );

        //When
        this.workspaceManager.active( givenWorkspace, givenData );
        final OSdmsWorkspace result = this.workspaceManager.getActive();

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenName, result.getName(), () -> "The result name should be the same as the given workspace name" );
        assertEquals( givenValue, result.getValue(), () -> "The result value should be the same as the given workspace value" );

        final List<String> patterns = result.getPatterns();
        assertNotNull( patterns, () -> "The result patterns should not be null" );
        assertEquals( 3, patterns.size(), () -> "The result patterns should have 3 elements" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| LIST |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Test
    public void givenWorkspacesRegistered_whenGetListOfWorkspacesRegistered_thenCheckIfEditTheReturnedListNoChangedTheWorkspacesRegistered(){
        //Given
        final List<OSdmsWorkspace> workspaces = this.workspaceManager.list();
        workspaces.clear();

        //When
        final List<OSdmsWorkspace> result = this.workspaceManager.list();

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( 1, result.size(), () -> "The result should have 1 element" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| EXISTS |-------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Test
    public void givenWorkspaceRegister_whenCallExistsByName_thenReturnsTrue(){
        //Given
        final String givenName = "workspace";
        final String givenValue = "/wk/{id}";
        final OSdmsWorkspace givenWorkspace = new OSdmsWorkspace( givenName, givenValue, Collections.emptyList() );
        this.workspaceManager.register( givenWorkspace );

        //When
        final boolean result = this.workspaceManager.exists( givenWorkspace.getName() );

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertTrue( result, () -> "The result should be true" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

    @Test
    public void givenWorkspaceRegister_whenCallExistsByWorkspace_thenReturnsTrue(){
        //Given
        final String givenName = "workspace";
        final String givenValue = "/wk/{id}";
        final OSdmsWorkspace givenWorkspace = new OSdmsWorkspace( givenName, givenValue, Collections.emptyList() );
        this.workspaceManager.register( givenWorkspace );

        //When
        final boolean result = this.workspaceManager.exists( givenWorkspace );

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertTrue( result, () -> "The result should be true" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

    @Test
    public void givenWorkspaceNotRegister_whenCallExistsByName_thenReturnsFalse(){
        //When
        final boolean result = this.workspaceManager.exists( "name" );

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertFalse( result, () -> "The result should be false" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

    @Test
    public void givenWorkspaceNotRegister_whenCallExistsByWorkspace_thenReturnsFalse(){
        //When
        final boolean result = this.workspaceManager.exists( "name" );

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertFalse( result, () -> "The result should be false" );

        verify( this.workspaceManagerAutoRegister, times( 1 ) ).run( any() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
