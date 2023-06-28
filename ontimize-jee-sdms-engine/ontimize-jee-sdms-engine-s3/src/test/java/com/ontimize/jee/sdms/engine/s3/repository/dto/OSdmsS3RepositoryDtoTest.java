package com.ontimize.jee.sdms.engine.s3.repository.dto;


import com.amazonaws.services.s3.model.*;
import com.ontimize.jee.sdms.common.zip.OSdmsZipData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OSdmsS3RepositoryDtoTest {


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
    void givenAValidS3Object_whenSetS3Object_thenDataIsSet( final String givenKey, final String expectedPrefix, final String expectedName ){
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
    void givenAValidS3ObjectSummary_whenSetS3ObjectSummary_thenDataIsSet( final String givenKey, final String expectedPrefix, final String expectedName ){
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
    void givenAValidS3ObjectMetadata_whenSetS3ObjectMetadata_thenDataIsSet(){
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
    void givenAValidFolderKey_whenSetFolderData_thenFolderDataIsSet( final String givenKey, final String expectedPrefix, final String expectedName ) {
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
    void givenAValidKeyAndSpecificWorkspaces_whenSetRelativeKey_thenRelativeKeyIsSet( final String givenKey, final String expectedRelativeKey ) {
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
    void givenAValidKeyAndSpecificWorkspaces_whenSetRelativePrefix_thenRelativePrefixIsSet( final String givenPrefix, final String expectedRelativePrefix ) {
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
// --------| TO MAP |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Test
    void givenOSdmsS3RepositoryDto_whenCallToMap_thenBuildACorrectMap() {
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

        assertEquals( givenBucket, result.get( "bucket" ), () -> "Unexpected bucket" );
        assertEquals( givenKey, result.get( "key" ), () -> "Unexpected key" );
        assertEquals( expectedRelativeKey, result.get( "relativeKey" ), () -> "Unexpected relativeKey" );
        assertEquals( expectedPrefix, result.get( "prefix" ), () -> "Unexpected prefix" );
        assertEquals( expectedRelativePrefix, result.get( "relativePrefix" ), () -> "Unexpected relativePrefix" );
        assertEquals( expectedName, result.get( "name" ), () -> "Unexpected name" );
        assertEquals( givenOwnerDisplayName, result.get( "owner" ), () -> "Unexpected owner" );
        assertEquals( givenSize, result.get( "size" ), () -> "Unexpected size" );
        assertFalse( ( boolean ) result.get( "folder" ), () -> "The result should not be a folder" );
        assertEquals( dto.getCreationDate().getTime(), result.get( "creationDate" ), () -> "Unexpected creationDate" );
        assertEquals( dto.getLastModified().getTime(), result.get( "lastModified" ), () -> "Unexpected lastModified" );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// --------| DATA TO ZIP |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Test
    void givenOSdmsS3RepositoryDto_whenCallGetDataToZip_thenOSdmsZipData() {
        //Expected
        final String expectedFileName = "entity_1_proof.txt";

        //Given
        final String givenKey = "/entity/1/proof.txt";
        final String givenName = "proof.txt";
        final boolean givenFolder = false;
        final S3ObjectInputStream givenS3ObjectInputStream = Mockito.mock( S3ObjectInputStream.class );

        //Set Data in DTO
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        dto.setKey( givenKey );
        dto.setName( givenName );
        dto.setFolder( givenFolder );
        dto.setFile( givenS3ObjectInputStream );

        //When
        final OSdmsZipData result = dto.getDataToZip();

        //Then
        assertNotNull( result, () -> "The result should not be null" );

        final InputStream inputStream = result.getInputStream();
        assertNotNull( inputStream, () -> "The inputStream should not be null" );

        final String fileName = result.getFileName();
        assertEquals( expectedFileName, fileName, () -> "Unexpected fileName" );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// --------| CONSTRUCTOR |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Test
    void givenS3Data_whenCallConstructor_thenCreateNewInstanceWithSameData(){
        //Given
        final String givenBucket = "bucket";
        final String givenKey = "key";
        final Long givenSize = 1L;
        final String givenOwnerDisplayName = "owner";
        final Date givenLastModified = new Date();

        //ObjectMetadata
        final ObjectMetadata givenObjectMetadata = Mockito.mock( ObjectMetadata.class );
        when( givenObjectMetadata.getContentLength() ).thenReturn( givenSize );
        when( givenObjectMetadata.getUserMetadata() ).thenReturn( Collections.EMPTY_MAP );

        //S3Object
        final S3Object givenS3Object = Mockito.mock( S3Object.class );
        when( givenS3Object.getKey() ).thenReturn( givenKey );
        when( givenS3Object.getBucketName() ).thenReturn( givenBucket );
        when( givenS3Object.getObjectMetadata() ).thenReturn( givenObjectMetadata );
        when( givenS3Object.getObjectContent() ).thenReturn( Mockito.mock( S3ObjectInputStream.class ) );

        //S3ObjectSummary
        final S3ObjectSummary givenS3ObjectSummary = Mockito.mock( S3ObjectSummary.class );
        when( givenS3ObjectSummary.getKey() ).thenReturn( givenKey );
        when( givenS3ObjectSummary.getBucketName() ).thenReturn( givenBucket );
        when( givenS3ObjectSummary.getSize() ).thenReturn( givenSize );
        when( givenS3ObjectSummary.getLastModified() ).thenReturn( givenLastModified );
        when( givenS3ObjectSummary.getOwner() ).thenReturn( Mockito.mock( Owner.class ) );
        when( givenS3ObjectSummary.getOwner().getDisplayName() ).thenReturn( givenOwnerDisplayName );

        //When
        final OSdmsS3RepositoryDto result = new OSdmsS3RepositoryDto( givenS3Object, givenS3ObjectSummary, givenObjectMetadata );

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        verify( givenS3Object, times( 1 ) ).getKey();
        verify( givenS3ObjectSummary, times( 1 ) ).getKey();
        verify( givenObjectMetadata, times( 2 ) ).getContentLength();
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// --------| SETTERS AND GETTERS |----------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    //Bucket
    @Test
    void givenBucketNameAsString_whenCallSetBucket_thenCheckTheNewValueWithCallGetter(){
        //Given
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        final String givenBucketName = "bucket";

        //When
        dto.setBucket( givenBucketName );

        //Then
        final String result = dto.getBucket();
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenBucketName, result, () -> "Unexpected result" );
    }

    //Key
    @Test
    void givenKeyAsString_whenCallSetKey_thenCheckTheNewValueWithCallGetter(){
        //Given
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        final String givenKey = "key";

        //When
        dto.setKey( givenKey );

        //Then
        final String result = dto.getKey();
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenKey, result, () -> "Unexpected result" );
    }

    //Relative Key
    @Test
    void givenRelativeKeyAsString_whenCallSetRelativeKey_thenCheckTheNewValueWithCallGetter(){
        //Given
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        final String givenRelativeKey = "relativeKey";

        //When
        dto.setRelativeKey( givenRelativeKey );

        //Then
        final String result = dto.getRelativeKey();
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenRelativeKey, result, () -> "Unexpected result" );
    }

    //Relative Prefix
    @Test
    void givenRelativePrefixAsString_whenCallSetRelativePrefix_thenCheckTheNewValueWithCallGetter(){
        //Given
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        final String givenRelativePrefix = "relativePrefix";

        //When
        dto.setRelativePrefix( givenRelativePrefix );

        //Then
        final String result = dto.getRelativePrefix();
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenRelativePrefix, result, () -> "Unexpected result" );
    }

    //Prefix
    @Test
    void givenPrefixAsString_whenCallSetPrefix_thenCheckTheNewValueWithCallGetter(){
        //Given
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        final String givenPrefix = "prefix";

        //When
        dto.setPrefix( givenPrefix );

        //Then
        final String result = dto.getPrefix();
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenPrefix, result, () -> "Unexpected result" );
    }

    //Name
    @Test
    void givenNameAsString_whenCallSetName_thenCheckTheNewValueWithCallGetter(){
        //Given
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        final String givenName = "name";

        //When
        dto.setName( givenName );

        //Then
        final String result = dto.getName();
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenName, result, () -> "Unexpected result" );
    }

    //Owner
    @Test
    void givenOwnerAsString_whenCallSetOwner_thenCheckTheNewValueWithCallGetter(){
        //Given
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        final String givenOwner = "owner";

        //When
        dto.setOwner( givenOwner );

        //Then
        final String result = dto.getOwner();
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenOwner, result, () -> "Unexpected result" );
    }

    //Size
    @Test
    void givenSizeAsLong_whenCallSetSize_thenCheckTheNewValueWithCallGetter(){
        //Given
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        final Long givenSize = 1L;

        //When
        dto.setSize( givenSize );

        //Then
        final Long result = dto.getSize();
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenSize, result, () -> "Unexpected result" );
    }

    //Folder
    @Test
    void givenFolderFlagAsBoolean_whenCallSetFolder_thenCheckTheNewValueWithCallGetter(){
        //Given
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        final boolean givenFolderFlag = true;

        //When
        dto.setFolder( givenFolderFlag );

        //Then
        final boolean result = dto.isFolder();
        assertTrue( result, () -> "The result should be true" );
    }

    //Creation Date
    @Test
    void givenCreationDateAsDate_whenCallSetCreationDate_thenCheckTheNewValueWithCallGetter() {
        //Given
        final Date givenCreationDate = new Date();
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();

        //When
        dto.setCreationDate( givenCreationDate );

        //Then
        final Date result = dto.getCreationDate();
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenCreationDate, result, () -> "Unexpected result" );
    }

    @Test
    void givenCreationDateAsString_whenCallSetCreationDate_thenCheckTheNewValueWithCallGetter() {
        final SimpleDateFormat formatDate = new SimpleDateFormat( "dd/MM/yyyy-HH:mm:ss" );

        //Given
        final String givenCreationDate = "27/06/2023-10:30:20";
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();

        //When
        dto.setCreationDate( givenCreationDate );

        //Then
        final Date result = dto.getCreationDate();
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenCreationDate, formatDate.format( result ), () -> "Unexpected result" );
    }

    //Creation Date
    @Test
    void givenLastModifiedAsDate_whenCallSetLastModified_thenCheckTheNewValueWithCallGetter() {
        //Given
        final Date givenLastModified = new Date();
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();

        //When
        dto.setLastModified( givenLastModified );

        //Then
        final Date result = dto.getLastModified();
        assertNotNull( result, () -> "The result should not be null" );
        assertEquals( givenLastModified, result, () -> "Unexpected result" );
    }

    //Metadata
    @Test
    void givenMetadadataAsMap_whenCallSetMetadata_thenCheckTheNewValueWithCallGetter() {
        //Given
        final int expectedSize = 10;
        final Map<String, Object> givenMetadata = Mockito.mock( Map.class );
        when( givenMetadata.size() ).thenReturn( expectedSize );

        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();

        //When
        dto.setMetadata( givenMetadata );

        //Then
        final Map<String, Object> result = dto.getMetadata();
        assertNotNull( result, () -> "The result should not be null" );
        assertFalse( result.isEmpty(), () -> "The result should not be empty" );
        assertEquals( expectedSize, result.size(), () -> "The result should not expected size" );
    }

    //File
    @Test
    void givenFileAsS3ObjectInputStream_whenCallSetFile_thenCheckTheNewValueWithCallGetter() {
        //Given
        final S3ObjectInputStream givenFile = Mockito.mock( S3ObjectInputStream.class );
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();

        //When
        dto.setFile( givenFile );

        //Then
        final InputStream result = dto.getFile();
        assertNotNull( result, () -> "The result should not be null" );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// --------| TO STRING |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Test
    void givenOSdmsS3RepositoryDto_whenCallToString_thenCorrectStringRepresentation() {
        //Given
        final String givenKey = "/entity/1/proof.txt";
        final String givenName = "proof.txt";
        final boolean givenFolder = false;
        final S3ObjectInputStream givenS3ObjectInputStream = Mockito.mock( S3ObjectInputStream.class );

        //Set Data in DTO
        final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
        dto.setKey( givenKey );
        dto.setName( givenName );
        dto.setFolder( givenFolder );
        dto.setFile( givenS3ObjectInputStream );

        //When
        final String result = dto.toString();

        //Then
        assertNotNull( result, () -> "The result should not be null" );
        assertTrue( result.length() > 220, () -> "The result size should be greater than 220" );

    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
