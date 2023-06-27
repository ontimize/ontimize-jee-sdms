package com.ontimize.jee.sdms.common.path.validator;

import com.ontimize.jee.sdms.common.path.builder.IOSdmsPathBuilder;
import com.ontimize.jee.sdms.common.workspace.OSdmsWorkspace;
import com.ontimize.jee.sdms.common.workspace.autoregister.IOSdmsWorkspaceManagerAutoRegister;
import com.ontimize.jee.sdms.common.workspace.manager.IOSdmsWorkspaceManager;
import com.ontimize.jee.sdms.common.workspace.manager.OSdmsWorkspaceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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
public class OSdmsPathValidatorTest {
    private @InjectMocks IOSdmsPathValidator oSdmsPathValidator = new OSdmsPathValidator();

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IS PATTERN |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @ParameterizedTest( name = "Pattern: {0}" )
    @ValueSource( strings = { "/{id}", "/{id}/", "/{id}/test", "/{id}/test/", "/{id}/test/{id}", "/{id}/test/{id}/", "/{id}/test/{id}/test", "/{id}/test/{id}/test/" } )
    public void givenPattern_whenCallIsPattern_thenTrue( final String pattern ) {
        //When - Then
        assertTrue( this.oSdmsPathValidator.isPattern( pattern ) );
    }

    @ParameterizedTest( name = "Pattern: {0}" )
    @ValueSource( strings = { "/5}", "/5/", "/5/test", "/5/test/", "/5/test/5", "/5/test/5/", "/5/test/5/test", "/5/test/5/test/" } )
    public void givenNoPattern_whenCallIsPattern_thenFalse( final String pattern ) {
        //When - Then
        assertFalse( this.oSdmsPathValidator.isPattern( pattern ) );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| VALIDATE |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @ParameterizedTest( name = "Path: {0}, Pattern: {1}" )
    @CsvSource({
            "path,",
            ",pattern",
            ","
    })
    public void givenNullParameters_whenCallValidateWithSingleParameters_thenReturnFalse( final String path, final String pattern ){
        //When - Then
        assertFalse( this.oSdmsPathValidator.validate( path, pattern ) );
    }

    @ParameterizedTest( name = "Path: {0}, Pattern: {1}" )
    @CsvSource({
            "/entity/1,/entity/{id}",
            "/entity/1/,/entity/{id}",
            "entity/1,entity/{id}",
            "entity/1/,entity/{id}",
            "entity/1,/entity/{id}",
            "entity/1/,/entity/{id}",
            "/entity/1,entity/{id}",
            "/entity/1/,entity/{id}",
            "/entity/1/proof/2,entity/{id-1}/proof/{id-2}",
            "entity/1/proof/2,entity/{id-1}/proof/{id-2}",
            "/entity/1/proof/2/,entity/{id-1}/proof/{id-2}",
            "entity/1/proof/2/,entity/{id-1}/proof/{id-2}"
    })
    public void givenValidParameters_whenCallValidateWithSingleParameters_thenReturnTrue( final String path, final String pattern ){
        //When - Then
        assertTrue( this.oSdmsPathValidator.validate( path, pattern ) );
    }

    @ParameterizedTest( name = "Path: {0}, Pattern: {1}" )
    @CsvSource({
            "/entity,/entity/{id}",
            "/entity/,/entity/{id}",
            "entity,entity/{id}",
            "entity/,entity/{id}",
            "entity,/entity/{id}",
            "entity/,/entity/{id}",
            "/entity,entity/{id}",
            "/entity/,entity/{id}",
            "entity/1,entity/{id-1}/proof/{id-2}",
            "entity/1/,entity/{id-1}/proof/{id-2}"
    })
    public void givenInvalidParameters_whenCallValidateWithSingleParameters_thenReturnTrue( final String path, final String pattern ){
        //When - Then
        assertFalse( this.oSdmsPathValidator.validate( path, pattern ) );
    }

    @ParameterizedTest( name = "Path: {0}" )
    @CsvSource({
            "/entity/1",
            "entity/1",
            "/entity/1/example/2",
            "entity/1/example/2",
            "new-entity/1/image/2000/top",
            "/new-entity/1/image/2000/top",
            "new-entity/1/image/2000/top/example/3",
            "/new-entity/1/image/2000/top/example/3"
    })
    public void givenValidPAth_whenCallValidateWithMultiplePatterns_thenReturnTrue( final String path ){
        //Given
        final List<String> patterns = List.of( "/entity/{id}", "/new-entity/{id-1}/image/{id-2}/top" );

        //When - Then
        assertTrue( this.oSdmsPathValidator.validate( path, patterns ) );
    }

    @ParameterizedTest( name = "Path: {0}" )
    @CsvSource({
            "/entity/1",
            "entity/1",
            "/entity/1/example/2",
            "entity/1/example/2",
            "new-entity/1/image/2000/top",
            "/new-entity/1/image/2000/top",
            "new-entity/1/image/2000/top/example/3",
            "/new-entity/1/image/2000/top/example/3"
    })
    public void givenValidPAth_whenCallValidate_thenReturnTrue( final String path ){
        //When - Then
        assertTrue( this.oSdmsPathValidator.validate( path ) );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
