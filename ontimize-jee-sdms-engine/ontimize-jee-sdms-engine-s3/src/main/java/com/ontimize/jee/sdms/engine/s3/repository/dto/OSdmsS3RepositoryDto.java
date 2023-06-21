package com.ontimize.jee.sdms.engine.s3.repository.dto;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ontimize.jee.sdms.common.response.builder.IOSdmsMappeable;
import com.ontimize.jee.sdms.common.zip.IOSdmsZippeable;
import com.ontimize.jee.sdms.common.zip.OSdmsZipData;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class that represents the data in response of the S3 Repository.
 *
 * @see IOSdmsMappeable
 * @see IOSdmsZippeable
 */
public class OSdmsS3RepositoryDto implements IOSdmsMappeable, IOSdmsZippeable {

    /** The name of the file that marks a folder in S3 */
    public final static String FILE_NAME_MARK_FOLDER = ".ontimizeSdmsFolder";

    /** The bucket name of S3 */
    private String bucket;

    /** The key of S3 */
    private String key;

    /** The relative key of S3 */
    private String relativeKey;

    /** The prefix of S3 */
    private String prefix;

    /** The relative prefix of S3 */
    private String relativePrefix;

    /** The name of S3 object */
    private String name;

    /** The owner of S3 object */
    private String owner;

    /** The size of S3 object */
    private Long size;

    /** Flag that indicates if the object is a folder */
    private boolean folder = false;

    /** The last modified date of S3 object */
    private Date lastModified;

    private Date creationDate;

    /** The metadata of S3 object */
    private Map<String, Object> metadata;

    /** The bytes of S3 file */
    private InputStream file;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3RepositoryDto() {
    }

    public OSdmsS3RepositoryDto( final S3Object s3Object, final S3ObjectSummary s3ObjectSummary, final ObjectMetadata objectMetadata ) {
        this.set( s3Object );
        this.set( s3ObjectSummary );
        this.set( objectMetadata );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| GETTERS AND SETTERS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public String getBucket() {
        return this.bucket;
    }

    public void setBucket( final String bucket ) {
        this.bucket = bucket;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey( final String key ) {
        this.key = key;
    }

    public String getRelativeKey() {
        return this.relativeKey;
    }

    public void setRelativeKey( final String relativeKey ) {
        this.relativeKey = relativeKey;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix( final String prefix ) {
        this.prefix = prefix;
    }

    public String getRelativePrefix() {
        return this.relativePrefix;
    }

    public void setRelativePrefix( final String relativePrefix ) {
        this.relativePrefix = relativePrefix;
    }

    public String getName() {
        return this.name;
    }

    public void setName( final String name ) {
        this.name = name;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner( final String owner ) {
        this.owner = owner;
    }

    public Long getSize() {
        return this.size;
    }

    public void setSize( final Long size ) {
        this.size = size;
    }

    public boolean isFolder() {
        return this.folder;
    }

    public void setFolder( final boolean folder ) {
        this.folder = folder;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate( final Date creationDate ) {
        this.creationDate = creationDate;
    }

    public void setCreationDate( final String creationDate ) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern( "dd/MM/yyyy-HH:mm:ss" );
        LocalDateTime localDateTime = LocalDateTime.parse( creationDate, dateTimeFormatter );
        this.creationDate = Date.from( localDateTime.atZone( ZoneId.systemDefault() ).toInstant() );
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified( final Date lastModified ) {
        this.lastModified = lastModified;
    }

    public Map<String, Object> getMetadata() {
        return this.metadata;
    }

    public void setMetadata( final Map<String, Object> metadata ) {
        this.metadata = metadata;
    }

    public InputStream getFile() {
        return this.file;
    }

    public void setFile( final InputStream file ) {
        this.file = file;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| SET METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Method that sets the bucket name, key and prefix, the bytes of file and the file metadata from S3Object.
     *
     * @param s3Object The S3Object.
     *
     * @see S3Object
     */
    public void set( final S3Object s3Object ) {
        this.processKey( s3Object.getKey() );
        this.bucket = s3Object.getBucketName();
        this.file = s3Object.getObjectContent();
        final ObjectMetadata objectMetadata = s3Object.getObjectMetadata();
        this.set( objectMetadata );
    }


    /**
     * Method that sets the bucket name, key and prefix, the object size, the object owner and the last modified date
     * from S3ObjectSummary.
     *
     * @param s3ObjectSummary The S3ObjectSummary.
     *
     * @see S3ObjectSummary
     */
    public void set( final S3ObjectSummary s3ObjectSummary ) {
        this.processKey( s3ObjectSummary.getKey() );
        this.bucket = s3ObjectSummary.getBucketName();
        this.size = s3ObjectSummary.getSize();
        this.lastModified = s3ObjectSummary.getLastModified();
        final Owner owner = s3ObjectSummary.getOwner();
        if( owner != null ) this.owner = owner.getDisplayName();
    }


    /**
     * Method that sets the bucket name, key and prefix, the object size and the object metadata from ObjectMetadata.
     *
     * @param objectMetadata The ObjectMetadata.
     *
     * @see ObjectMetadata
     */
    public void set( final ObjectMetadata objectMetadata ) {
        if( objectMetadata != null ) {
            this.size = objectMetadata.getContentLength();
            this.metadata = objectMetadata.getRawMetadata();
            final Map<String, String> userMetadata = objectMetadata.getUserMetadata();
            if( userMetadata != null && userMetadata.containsKey( "creation_date" ) ) {
                this.setCreationDate( userMetadata.get( "creation_date" ) );
            }
        }
    }


    /**
     * Method that sets the folder data from bucket and prefix.
     *
     * @param bucket The bucket name.
     * @param prefix The prefix.
     */
    public void setFolderData( final String bucket, final String prefix ) {
        this.processKey( prefix );
        this.key = prefix;
        this.bucket = bucket;
        this.folder = true;
    }


    /**
     * Method that sets the relative key from workspace list.
     *
     * @param workspaces The workspace list.
     */
    public void setRelativeKey( final List<String> workspaces ) {
        this.relativeKey = this.key;
        workspaces.forEach( target -> this.relativeKey = this.relativeKey.replaceFirst( target, "" ) );
    }


    /**
     * Method that sets the relative prefix from workspace list.
     *
     * @param workspaces The workspace list.
     */
    public void setRelativePrefix( final List<String> workspaces ) {
        this.relativePrefix = this.prefix;
        workspaces.forEach( target -> this.relativePrefix = this.relativePrefix.replaceFirst( target, "" ) );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public Map<?, ?> toMap() {
        final SimpleDateFormat simpleDateFormatter = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
        final Map<String, Object> result = new HashMap<>();

        result.put( "bucket", this.bucket );
        result.put( "prefix", this.prefix );
        result.put( "relativePrefix", this.relativePrefix );
        result.put( "key", this.key );
        result.put( "relativeKey", this.relativeKey );
        result.put( "name", this.name );
        result.put( "owner", this.owner );
        result.put( "file", this.file );
        result.put( "size", this.size );
        result.put( "metadata", this.metadata );
        result.put( "folder", this.folder );

        String creationDate = null;
        if( this.creationDate != null ) creationDate = simpleDateFormatter.format( this.creationDate );
        result.put( "creationDate", creationDate );

        String lastModified = null;
        if( this.creationDate != null ) lastModified = simpleDateFormatter.format( this.lastModified );
        result.put( "lastModified", lastModified );

        return result;
    }

    @Override
    public OSdmsZipData getDataToZip() {
        OSdmsZipData result = null;
        if( ! this.folder && ! this.name.equals( OSdmsS3RepositoryDto.FILE_NAME_MARK_FOLDER ) ) {
            String fileName = this.key.replaceAll( "/", "." );
            if( fileName.endsWith( "." ) ) fileName = fileName.substring( 0, fileName.length() - 1 );
            result = new OSdmsZipData();
            result.setInputStream( this.file );
            result.setFileName( fileName );
        }
        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| UTILITIES |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Method that process the key to get the bucket name, key and prefix.
     *
     * @param key The key.
     */
    public void processKey( final String key ) {
        //Get key
        this.key = key;

        //Get name
        final String[] keyParts = key.split( "/" );
        final int lastPosition = keyParts.length - 1;
        if( lastPosition >= 0 ) {
            this.name = keyParts[ lastPosition ];
        }

        //Get Prefix
        if( this.name != null ) this.prefix = this.key.substring( 0, ( this.key.length() - this.name.length() ) );
        if( ! this.prefix.endsWith( "/" ) ) this.prefix = this.prefix.concat( "/" );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
}

