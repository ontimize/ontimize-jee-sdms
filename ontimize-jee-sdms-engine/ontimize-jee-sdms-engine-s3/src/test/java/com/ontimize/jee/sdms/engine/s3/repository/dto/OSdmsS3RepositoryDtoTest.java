package com.ontimize.jee.sdms.engine.s3.repository.dto;


import com.amazonaws.services.s3.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class OSdmsS3RepositoryDtoTest {


// ------------------------------------------------------------------------------------------------------------------ \\
// --------| SET (S3Object) |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @ParameterizedTest( name = "Given = [ Key {0} ], Expected: [ Prefix: {1}, Name: {2} ]" )
    @CsvSource({
            "/proof.txt,/,proof.txt",
            "proof.txt,/,proof.txt",
            "proof/proof.txt,proof/,proof.txt",
            "/proof/proof.txt,/proof/,proof.txt"
    })
    public void givenAValidS3Object_whenSetS3Object_thenDataIsSet( final String givenKey, final String expectedPrefix, final String expectedName ){
        final SimpleDateFormat formatDate = new SimpleDateFormat( "dd/MM/yyyy-HH:mm:ss" );

        //Given
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        final String givenBucket = "bucket";
        final Long givenSize = 1L;
        final String givenCreationDate = "27/06/2023-10:30:20";
        final S3ObjectInputStream givenFile = Mockito.mock( S3ObjectInputStream.class );

        final Map<String, String> givenUserMetadata = Mockito.mock( Map.class );
        when(  givenUserMetadata.containsKey( "creation_date" ) ).thenReturn( true );
        when(  givenUserMetadata.get( "creation_date" ) ).thenReturn( givenCreationDate );

        final ObjectMetadata givenObjectMetadata = Mockito.mock( ObjectMetadata.class );
        when( givenObjectMetadata.getRawMetadata() ).thenReturn( Mockito.mock( Map.class ) );
        when( givenObjectMetadata.getContentLength() ).thenReturn( givenSize );
        when( givenObjectMetadata.getUserMetadata() ).thenReturn( givenUserMetadata );

        final S3Object givenS3Object = Mockito.mock( S3Object.class );
        when( givenS3Object.getBucketName() ).thenReturn( givenBucket );
        when( givenS3Object.getKey() ).thenReturn( givenKey );
        when( givenS3Object.getObjectContent() ).thenReturn( givenFile );
        when( givenS3Object.getObjectMetadata() ).thenReturn( givenObjectMetadata );

        //When
        dto.set( givenS3Object );

        //Then
        final String bucket = dto.getBucket();
        final String key = dto.getKey();
        final String prefix = dto.getPrefix();
        final String name = dto.getName();
        final InputStream file = dto.getFile();
        final Date creationDate = dto.getCreationDate();
        final Long size = dto.getSize();
        final Map<String, Object> metadata = dto.getMetadata();

        assertFalse( dto.isFolder(), () -> "The object should not be a folder" );

        assertNotNull( bucket, () -> "The bucket should not be null" );
        assertNotNull( key, () -> "The key should not be null" );
        assertNotNull( prefix, () -> "The prefix should not be null" );
        assertNotNull( name, () -> "The name should not be null" );
        assertNotNull( file, () -> "The file should not be null" );
        assertNotNull( dto.getCreationDate(), () -> "The creation date should not be null" );
        assertNotNull( size, () -> "The size should not be null" );
        assertNotNull( metadata, () -> "The metadata should not be null" );

        assertNull( dto.getLastModified(), () -> "The last modified should be null" );
        assertNull( dto.getRelativeKey(), () -> "The relative key should be null" );
        assertNull( dto.getRelativePrefix(), () -> "The relative prefix should be null" );

        assertEquals( givenBucket, bucket, () -> "Unexpected bucket" );
        assertEquals( givenKey, key, () -> "Unexpected key" );
        assertEquals( expectedName, name, () -> String.format( "Unexpected name for key: %s", givenKey ) );
        assertEquals( expectedPrefix, prefix, () -> String.format( "Unexpected prefix for key: %s", givenKey ) );
        assertEquals( givenCreationDate, formatDate.format( creationDate ), () -> "Unexpected creation date" );
        assertEquals( givenSize, size, () -> "Unexpected size" );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// --------| SET (S3ObjectSummary) |--------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @ParameterizedTest( name = "Given = [ Key {0} ], Expected: [ Prefix: {1}, Name: {2} ]" )
    @CsvSource({
            "/proof.txt,/,proof.txt",
            "proof.txt,/,proof.txt",
            "proof/proof.txt,proof/,proof.txt",
            "/proof/proof.txt,/proof/,proof.txt"
    })
    public void givenAValidS3ObjectSummary_whenSetS3ObjectSummary_thenDataIsSet( final String givenKey, final String expectedPrefix, final String expectedName ){
        //Given
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        final String givenBucket = "bucket";
        final Long givenSize = 1L;
        final Date givenLastModified = new Date();
        final Owner givenOwner = Mockito.mock( Owner.class );

        final String givenOwnerDisplayName = "owner";
        when( givenOwner.getDisplayName() ).thenReturn( givenOwnerDisplayName );

        final S3ObjectSummary givenS3ObjectSummary = Mockito.mock( S3ObjectSummary.class );
        when( givenS3ObjectSummary.getKey() ).thenReturn( givenKey );
        when( givenS3ObjectSummary.getBucketName() ).thenReturn( givenBucket );
        when( givenS3ObjectSummary.getSize() ).thenReturn( givenSize );
        when( givenS3ObjectSummary.getLastModified() ).thenReturn( givenLastModified );
        when( givenS3ObjectSummary.getOwner() ).thenReturn( givenOwner );

        //When
        dto.set( givenS3ObjectSummary );

        //Then
        final String bucket = dto.getBucket();
        final String key = dto.getKey();
        final String prefix = dto.getPrefix();
        final String name = dto.getName();
        final Date lastModified = dto.getLastModified();
        final Long size = dto.getSize();
        final String owner = dto.getOwner();

        assertFalse( dto.isFolder(), () -> "The object should not be a folder" );

        assertNotNull( bucket, () -> "The bucket should not be null" );
        assertNotNull( key, () -> "The key should not be null" );
        assertNotNull( prefix, () -> "The prefix should not be null" );
        assertNotNull( name, () -> "The name should not be null" );
        assertNotNull( lastModified, () -> "The last modified should not be null" );
        assertNotNull( size, () -> "The size should not be null" );
        assertNotNull( owner, () -> "The owner should not be null" );

        assertNull( dto.getCreationDate(), () -> "The creation date should be null" );
        assertNull( dto.getMetadata(), () -> "The metadata should be null" );
        assertNull( dto.getFile(), () -> "The file should be null" );
        assertNull( dto.getRelativeKey(), () -> "The relative key should be null" );
        assertNull( dto.getRelativePrefix(), () -> "The relative prefix should be null" );

        assertEquals( givenBucket, bucket, () -> "Unexpected bucket" );
        assertEquals( givenKey, key, () -> "Unexpected key" );
        assertEquals( expectedName, name, () -> String.format( "Unexpected name for key: %s", givenKey ) );
        assertEquals( expectedPrefix, prefix, () -> String.format( "Unexpected prefix for key: %s", givenKey ) );
        assertEquals( givenLastModified, lastModified, () -> "Unexpected last modified" );
        assertEquals( givenSize, size, () -> "Unexpected size" );
        assertEquals( givenOwnerDisplayName, owner, () -> "Unexpected owner" );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// --------| SET (S3Object) |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Test
    public void givenAValidS3ObjectMetadata_whenSetS3ObjectMetadata_thenDataIsSet(){
        final SimpleDateFormat formatDate = new SimpleDateFormat( "dd/MM/yyyy-HH:mm:ss" );

        //Given
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        final Long givenSize = 1L;
        final String givenCreationDate = "27/06/2023-10:30:20";

        final Map<String, String> givenUserMetadata = Mockito.mock( Map.class );
        when(  givenUserMetadata.containsKey( "creation_date" ) ).thenReturn( true );
        when(  givenUserMetadata.get( "creation_date" ) ).thenReturn( givenCreationDate );

        final ObjectMetadata givenObjectMetadata = Mockito.mock( ObjectMetadata.class );
        when( givenObjectMetadata.getRawMetadata() ).thenReturn( Mockito.mock( Map.class ) );
        when( givenObjectMetadata.getContentLength() ).thenReturn( givenSize );
        when( givenObjectMetadata.getUserMetadata() ).thenReturn( givenUserMetadata );

        //When
        dto.set( givenObjectMetadata );

        //Then
        final Date creationDate = dto.getCreationDate();
        final Long size = dto.getSize();
        final Map<String, Object> metadata = dto.getMetadata();

        assertFalse( dto.isFolder(), () -> "The object should not be a folder" );

        assertNotNull( dto.getCreationDate(), () -> "The creation date should not be null" );
        assertNotNull( size, () -> "The size should not be null" );
        assertNotNull( metadata, () -> "The metadata should not be null" );

        assertNull( dto.getBucket(), () -> "The bucket should be null" );
        assertNull( dto.getKey(), () -> "The key should be null" );
        assertNull( dto.getPrefix(), () -> "The prefix should be null" );
        assertNull( dto.getName(), () -> "The name should be null" );
        assertNull( dto.getFile(), () -> "The file should be null" );
        assertNull( dto.getLastModified(), () -> "The last modified should be null" );
        assertNull( dto.getRelativeKey(), () -> "The relative key should be null" );
        assertNull( dto.getRelativePrefix(), () -> "The relative prefix should be null" );

        assertEquals( givenCreationDate, formatDate.format( creationDate ), () -> "Unexpected creation date" );
        assertEquals( givenSize, size, () -> "Unexpected size" );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// --------| SET FOLDER DATA |--------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @ParameterizedTest( name = "Given = [ Key: {0} ], Expected = [ Prefix: {1}, Name: {2} ]" )
    @CsvSource({
            "/,/,/",
            "proof,/,proof",
            "proof/subproof,proof/,subproof",
            "/proof,/,proof,",
            "/proof/subproof,/proof/,subproof",
            "proof/,/,proof",
            "proof/subproof/,proof/,subproof",
            "/proof/,/,proof",
            "/proof/subproof/,/proof/,subproof",
    })
    public void givenAValidFolderKey_whenSetFolderData_thenFolderDataIsSet( final String givenKey, final String expectedPrefix, final String expectedName ) {
        //Given
        final String givenBucket = "bucket";
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();

        //When
        dto.setFolderData( givenBucket, givenKey );

        //Then
        final String bucket = dto.getBucket();
        final String key = dto.getKey();
        final String prefix = dto.getPrefix();
        final String name = dto.getName();

        assertTrue( dto.isFolder(), () -> "The object should be a folder" );

        assertNotNull( key, () -> "The key should not be null" );
        assertNotNull( bucket, () -> "The bucket should not be null" );
        assertNotNull( prefix, () -> "The prefix should not be null" );
        assertNotNull( name, () -> "The name should not be null" );

        assertNull( dto.getSize(), () -> "The size should be null" );
        assertNull( dto.getOwner(), () -> "The owner should be null" );
        assertNull( dto.getCreationDate(), () -> "The creation date should be null" );
        assertNull( dto.getLastModified(), () -> "The last modified should be null" );
        assertNull( dto.getMetadata(), () -> "The metadata should be null" );
        assertNull( dto.getFile(), () -> "The file should be null" );
        assertNull( dto.getRelativeKey(), () -> "The relative key should be null" );
        assertNull( dto.getRelativePrefix(), () -> "The relative prefix should be null" );

        assertEquals( givenBucket, bucket, () -> "Unexpected bucket" );
        assertEquals( givenKey, key, () -> "Unexpected key" );
        assertEquals( expectedName, name, () -> String.format( "Unexpected name for key: %s", givenKey ) );
        assertEquals( expectedPrefix, prefix, () -> String.format( "Unexpected prefix for key: %s", givenKey ) );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// --------| SET RELATIVE DATA |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @ParameterizedTest( name = "Given = [ Key {0} ], Expected: [ RelativeKey: {1} ]" )
    @CsvSource({
            "entity/1,/",
            "entity/1/,/",
            "entity/5,/",
            "entity/5/,/",
            "entity/1/proof.txt,/proof.txt",
            "entity/5/proof.txt,/proof.txt",
            "entity/10/proof/proof.txt,/proof/proof.txt",
            "entity/50/proof/proof.txt,/proof/proof.txt",
            "entity/images/top/image.png,/image.png",
            "entity/images/top/profile/image.png,/profile/image.png"
    })
    public void givenAValidKeyAndSpecificWorkspaces_whenSetRelativeKey_thenRelativeKeyIsSet( final String givenKey, final String expectedRelativeKey ) {
        //Given
        final List<String> givenWorkspaces = Arrays.asList( "entity/1", "entity/5", "entity/10", "entity/50", "entity/images/top" );
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        dto.setKey( givenKey );

        //When
        dto.setRelativeKey( givenWorkspaces );

        //Then
        final String relativeKey = dto.getRelativeKey();

        assertNotNull( relativeKey, () -> "The relativeKey should not be null" );
        assertEquals( expectedRelativeKey, relativeKey, () -> String.format( "Unexpected relativeKey for key: %s", givenKey ) );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// --------| SET RELATIVE PREFIX |----------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @ParameterizedTest( name = "Given = [ Key {0} ], Expected: [ RelativeKey: {1} ]" )
    @CsvSource({
            "entity/1/,/",
            "entity/1,/",
            "entity/5,/",
            "entity/5/,/",
            "entity/10/proof/,/proof/",
            "entity/50/proof,/proof/",
            "entity/images/top/,/",
            "entity/images/top,/",
            "entity/images/top/profile/,/profile/",
            "entity/images/top/profile,/profile/"
    })
    public void givenAValidKeyAndSpecificWorkspaces_whenSetRelativePrefix_thenRelativePrefixIsSet( final String givenPrefix, final String expectedRelativePrefix ) {
        //Given
        final List<String> givenWorkspaces = Arrays.asList( "entity/1", "entity/5", "entity/10", "entity/50", "entity/images/top" );
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        dto.setPrefix( givenPrefix );

        //When
        dto.setRelativePrefix( givenWorkspaces );

        //Then
        final String relativeKey = dto.getRelativePrefix();

        assertNotNull( relativeKey, () -> "The relativePrefix should not be null" );
        assertEquals( expectedRelativePrefix, relativeKey, () -> String.format( "Unexpected relativePrefix for prefix: %s", givenPrefix ) );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// --------| SET CREATION DATE |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Test
    public void givenAValidCreationDateAsString_whenSetCreationDate_thenCreationDateIsSet() {
        final SimpleDateFormat formatDate = new SimpleDateFormat( "dd/MM/yyyy-HH:mm:ss" );

        //Given
        final String givenCreationDateAsString = "27/06/2023-10:30:20";
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();

        //When
        dto.setCreationDate( givenCreationDateAsString );

        //Then
        final Date creationDate = dto.getCreationDate();
        assertNotNull( creationDate, () -> "The creation date should not be null" );
        assertEquals( givenCreationDateAsString, formatDate.format( creationDate ), () -> "Unexpected bucket" );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// --------| TO MAP |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Test
    public void givenOSdmsS3RepositoryDto_whenCallToMap_thenBuildACorrectMap() {
        //Expected
        final String expectedName = "proof.txt";
        final String expectedPrefix = "/entity/1/";
        final String expectedRelativeKey = "/proof.txt";
        final String expectedRelativePrefix = "/";

        //Given
        final List<String> givenWorkspaces = Arrays.asList( "entity/1", "entity/5", "entity/10", "entity/50", "entity/images/top" );
        final String givenBucket = "bucket";
        final String givenKey = "/entity/1/proof.txt";
        final Long givenSize = 1L;
        final Date givenLastModified = new Date();
        final S3ObjectInputStream givenFile = Mockito.mock( S3ObjectInputStream.class );
        final String givenCreationDate = "27/06/2023-10:30:20";

        //Owner
        final Owner givenOwner = Mockito.mock( Owner.class );
        final String givenOwnerDisplayName = "owner";
        when( givenOwner.getDisplayName() ).thenReturn( givenOwnerDisplayName );

        //UserMetadata
        final Map<String, String> givenUserMetadata = Mockito.mock( Map.class );
        when(  givenUserMetadata.containsKey( "creation_date" ) ).thenReturn( true );
        when(  givenUserMetadata.get( "creation_date" ) ).thenReturn( givenCreationDate );

        //ObjectMetadata
        final ObjectMetadata givenObjectMetadata = Mockito.mock( ObjectMetadata.class );
        when( givenObjectMetadata.getRawMetadata() ).thenReturn( Mockito.mock( Map.class ) );
        when( givenObjectMetadata.getContentLength() ).thenReturn( givenSize );
        when( givenObjectMetadata.getUserMetadata() ).thenReturn( givenUserMetadata );

        //S3ObjectSummary
        final S3ObjectSummary givenS3ObjectSummary = Mockito.mock( S3ObjectSummary.class );
        when( givenS3ObjectSummary.getKey() ).thenReturn( givenKey );
        when( givenS3ObjectSummary.getBucketName() ).thenReturn( givenBucket );
        when( givenS3ObjectSummary.getSize() ).thenReturn( givenSize );
        when( givenS3ObjectSummary.getLastModified() ).thenReturn( givenLastModified );
        when( givenS3ObjectSummary.getOwner() ).thenReturn( givenOwner );

        //S3Object
        final S3Object givenS3Object = Mockito.mock( S3Object.class );
        when( givenS3Object.getBucketName() ).thenReturn( givenBucket );
        when( givenS3Object.getKey() ).thenReturn( givenKey );
        when( givenS3Object.getObjectContent() ).thenReturn( givenFile );
        when( givenS3Object.getObjectMetadata() ).thenReturn( givenObjectMetadata );

        //Set Data in DTO
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        dto.set( givenS3ObjectSummary );
        dto.set( givenS3Object );
        dto.setRelativeKey( givenWorkspaces );
        dto.setRelativePrefix( givenWorkspaces );

        //When
        final Map<?, ?> result = dto.toMap();

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( 13, result.size(), () -> "Unexpected size" );

        assertTrue( result.containsKey( "bucket" ), () -> "The result should contain the bucket" );
        assertNotNull( result.get( "bucket" ), () -> "The bucket should not be null" );
        assertEquals( givenBucket, result.get( "bucket" ), () -> "Unexpected bucket" );

        assertTrue( result.containsKey( "key" ), () -> "The result should contain the key" );
        assertNotNull( result.get( "key" ), () -> "The key should not be null" );
        assertEquals( givenKey, result.get( "key" ), () -> "Unexpected key" );

        assertTrue( result.containsKey( "relativeKey" ), () -> "The result should contain the relativeKey" );
        assertNotNull( result.get( "relativeKey" ), () -> "The relativeKey should not be null" );
        assertEquals( expectedRelativeKey, result.get( "relativeKey" ), () -> "Unexpected relativeKey" );

        assertTrue( result.containsKey( "prefix" ), () -> "The result should contain the prefix" );
        assertNotNull( result.get( "prefix" ), () -> "The prefix should not be null" );
        assertEquals( expectedPrefix, result.get( "prefix" ), () -> "Unexpected prefix" );

        assertTrue( result.containsKey( "relativePrefix" ), () -> "The result should contain the relativePrefix" );
        assertNotNull( result.get( "relativePrefix" ), () -> "The relativePrefix should not be null" );
        assertEquals( expectedRelativePrefix, result.get( "relativePrefix" ), () -> "Unexpected relativePrefix" );

        assertTrue( result.containsKey( "name" ), () -> "The result should contain the name" );
        assertNotNull( result.get( "name" ), () -> "The name should not be null" );
        assertEquals( expectedName, result.get( "name" ), () -> "Unexpected name" );

        assertTrue( result.containsKey( "owner" ), () -> "The result should contain the owner" );
        assertNotNull( result.get( "owner" ), () -> "The result should not be null" );
        assertEquals( givenOwnerDisplayName, result.get( "owner" ), () -> "Unexpected owner" );

        assertTrue( result.containsKey( "file" ), () -> "The result should contain the file" );
        assertNotNull(result.get( "file" ), () -> "The result should not be null" );

        assertTrue( result.containsKey( "size" ), () -> "The result should contain the size" );
        assertNotNull( result.get( "size" ), () -> "The size should not be null" );
        assertEquals( givenSize, result.get( "size" ), () -> "Unexpected size" );

        assertTrue( result.containsKey( "metadata" ), () -> "The result should contain the metadata" );
        assertNotNull( result.get( "metadata" ), () -> "The metadata should not be null" );

        assertTrue( result.containsKey( "folder" ), () -> "The result should contain the folder" );
        assertNotNull( result.get( "folder" ), () -> "The folder should not be null" );
        assertTrue( result.get( "folder" ) instanceof Boolean, () -> "The result should contain a boolean type" );
        assertFalse( ( boolean ) result.get( "folder" ), () -> "The result should not be a folder" );

        assertTrue( result.containsKey( "creationDate" ), () -> "The result should contain the creationDate" );
        assertNotNull( result.get( "creationDate" ), () -> "The creationDate should not be null" );
        assertTrue( result.get( "creationDate" ) instanceof Long, () -> "The result should contain a long type" );
        assertEquals( dto.getCreationDate().getTime(), result.get( "creationDate" ), () -> "Unexpected creationDate" );

        assertTrue( result.containsKey( "lastModified" ), () -> "The result should contain the lastModified" );
        assertNotNull( result.get( "lastModified" ), () -> "The lastModified should not be null" );
        assertTrue( result.get( "lastModified" ) instanceof Long, () -> "The result should contain a long type" );
        assertEquals( dto.getLastModified().getTime(), result.get( "lastModified" ), () -> "Unexpected lastModified" );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
