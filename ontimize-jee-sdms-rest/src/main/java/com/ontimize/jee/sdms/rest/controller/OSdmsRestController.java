package com.ontimize.jee.sdms.rest.controller;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.common.tools.CheckingTools;
import com.ontimize.jee.common.tools.ReflectionTools;
import com.ontimize.jee.server.rest.ORestController;
import com.ontimize.jee.sdms.common.dto.OSdmsRestDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;


/**
 * The Class OSdmsRestController to aggregate all the DMS endpoints and CRUD endpoints to entities in the application.
 *
 * @param <S> the generic type
 *
 * @see ORestController
 */
public abstract class OSdmsRestController<S> extends ORestController<S> {

    /**
     * The LOGGER constant, which is an instance of org.slf4j.Logger used for logging events and diagnostic messages
     * during program execution.
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger( OSdmsRestController.class );

// ------------------------------------------------------------------------------------------------------------------ \\

    protected static final String METHOD_DMS_FIND_BY_ID = "SdmsFindById";

    protected static final String METHOD_DMS_FIND = "SdmsFind";

    protected static final String METHOD_DMS_DOWNLOAD_BY_ID = "SdmsDownloadById";

    protected static final String METHOD_DMS_DOWNLOAD= "SdmsDownload";

    protected static final String METHOD_DMS_UPLOAD = "SdmsUpload";

    protected static final String METHOD_DMS_CREATE = "SdmsCreate";

    protected static final String METHOD_DMS_UPDATE = "SdmsUpdate";

    protected static final String METHOD_DMS_COPY = "SdmsCopy";

    protected static final String METHOD_DMS_MOVE = "SdmsMove";

    protected static final String METHOD_DMS_DELETE_BY_ID = "SdmsDeleteById";

    protected static final String METHOD_DMS_DELETE = "SdmsDelete";

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * The KEY_ENTITY constant, which is a constant containing the name of the path variable representing the service
     * entity.
     */
    protected static final String KEY_ENTITY = "entity";

    protected static final String KEY_ID = "id";

    protected static final String KEY_DATA = "data";

    protected static final String KEY_FILE = "file";

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * The MSG_SERVICE_IS_NULL constant, which is a constant containing the error message used when the provided
     * service is null and cannot be used to process requests.
     */
    protected static final String MSG_SERVICE_IS_NULL = "Service is null";

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| FIND ENDPOINT |------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @GetMapping(
            path = "/{" + KEY_ENTITY + "}/sdms/find/id/{" + KEY_ID + "}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityResult> sdmsFindById(
            @PathVariable( value = KEY_ENTITY, required = true ) final String entity,
            @PathVariable( value = KEY_ID, required = true ) final Serializable id,
            @RequestParam( value= KEY_DATA, required = false ) final OSdmsRestDataDto data
    ){

        CheckingTools.failIf(this.getService() == null, NullPointerException.class, MSG_SERVICE_IS_NULL );

        //Build the method name
        final String methodName = new StringBuffer()
                .append( entity )
                .append( METHOD_DMS_FIND_BY_ID )
                .toString();

        try {
            //Service Request
            final EntityResult entityResult = (EntityResult) ReflectionTools.invoke( this.getService(), methodName, id, data );

            //Return ResponseEntity
            return this.createResponseEntitiy( entityResult );
        }
        catch( final Exception exception ){
            return this.createResponseEntityInternalServerError( exception );
        }
    }



    @PostMapping(
            path = "/{" + KEY_ENTITY + "}/sdms/find",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityResult> sdmsFind(
            @PathVariable( value = KEY_ENTITY, required = true ) final String entity,
            @RequestBody( required = true ) final OSdmsRestDataDto data
    ){

        CheckingTools.failIf(this.getService() == null, NullPointerException.class, MSG_SERVICE_IS_NULL );

        //Build the method name
        final String methodName = new StringBuffer()
                .append( entity )
                .append( METHOD_DMS_FIND )
                .toString();

        try {
            //Service Request
            final EntityResult entityResult = (EntityResult) ReflectionTools.invoke( this.getService(), methodName, data );

            //Return ResponseEntity
            return this.createResponseEntitiy( entityResult );
        }
        catch( final Exception exception ){
            return this.createResponseEntityInternalServerError( exception );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DOWNLOAD ENDPOINT |-------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @GetMapping(
            path = "/{" + KEY_ENTITY + "}/sdms/download/id/{" + KEY_ID + "}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<EntityResult> sdmsDownloadById(
            @PathVariable( value = KEY_ENTITY, required = true ) final String entity,
            @PathVariable( value = KEY_ID, required = true ) final Serializable id,
            @RequestParam( value= KEY_DATA, required = false ) final OSdmsRestDataDto data
    ){

        CheckingTools.failIf(this.getService() == null, NullPointerException.class, MSG_SERVICE_IS_NULL );

        //Build the method name
        final String methodName = new StringBuffer()
                .append( entity )
                .append( METHOD_DMS_DOWNLOAD_BY_ID )
                .toString();

        try {
            //Service Request
            final EntityResult entityResult = (EntityResult) ReflectionTools.invoke( this.getService(), methodName, id, data );

            //Return ResponseEntity
            return this.createResponseEntityWithDocument( entityResult );
        }
        catch( final Exception exception ){
            return this.createResponseEntityInternalServerError( exception );
        }
    }



    @PostMapping(
            path = "/{" + KEY_ENTITY + "}/sdms/download",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<EntityResult> sdmsDownload(
            @PathVariable( value = KEY_ENTITY, required = true ) final String entity,
            @RequestBody( required = true ) final OSdmsRestDataDto data
    ){

        CheckingTools.failIf(this.getService() == null, NullPointerException.class, MSG_SERVICE_IS_NULL );

        //Build the method name
        final String methodName = new StringBuffer()
                .append( entity )
                .append( METHOD_DMS_DOWNLOAD )
                .toString();

        try {
            //Service Request
            final EntityResult entityResult = (EntityResult) ReflectionTools.invoke( this.getService(), methodName, data );

            //Return ResponseEntity
            return this.createResponseEntityWithDocument( entityResult );
        }
        catch( final Exception exception ){
            return this.createResponseEntityInternalServerError( exception );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| UPLOAD ENDPOINT |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PostMapping(
            path = "/{" + KEY_ENTITY + "}/sdms/upload",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityResult> sdmsUpload(
            @PathVariable( value = KEY_ENTITY, required = true ) final String entity,
            @RequestParam( value = KEY_DATA, required = true ) final OSdmsRestDataDto data,
            @RequestParam( value = KEY_FILE, required = true ) final MultipartFile file
    ){

        CheckingTools.failIf(this.getService() == null, NullPointerException.class, MSG_SERVICE_IS_NULL );

        //Build the method name
        final String methodName = new StringBuffer()
                .append( entity )
                .append( METHOD_DMS_UPLOAD )
                .toString();

        try {
            //Service Request
            final EntityResult entityResult = (EntityResult) ReflectionTools.invoke( this.getService(), methodName, data, file );

            //Return ResponseEntity
            return this.createResponseEntitiy( entityResult );
        }
        catch( final Exception exception ){
            return this.createResponseEntityInternalServerError( exception );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| CREATE ENDPOINT |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PostMapping(
            path = "/{" + KEY_ENTITY + "}/sdms/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityResult> sdmsCreate(
            @PathVariable( value = KEY_ENTITY, required = true ) final String entity,
            @RequestBody( required = true ) final OSdmsRestDataDto data
    ){

        CheckingTools.failIf(this.getService() == null, NullPointerException.class, MSG_SERVICE_IS_NULL );

        //Build the method name
        final String methodName = new StringBuffer()
                .append( entity )
                .append( METHOD_DMS_CREATE )
                .toString();

        try {
            //Service Request
            final EntityResult entityResult = (EntityResult) ReflectionTools.invoke( this.getService(), methodName, data );

            //Return ResponseEntity
            return this.createResponseEntitiy( entityResult );
        }
        catch( final Exception exception ){
            return this.createResponseEntityInternalServerError( exception );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| UPDATE ENDPOINT |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PutMapping(
            path = "/{" + KEY_ENTITY + "}/sdms/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityResult> sdmsUpdate(
            @PathVariable( value = KEY_ENTITY, required = true ) final String entity,
            @RequestBody( required = true ) final OSdmsRestDataDto data
    ){

        CheckingTools.failIf(this.getService() == null, NullPointerException.class, MSG_SERVICE_IS_NULL );

        //Build the method name
        final String methodName = new StringBuffer()
                .append( entity )
                .append( METHOD_DMS_UPDATE )
                .toString();

        try {
            //Service Request
            final EntityResult entityResult = (EntityResult) ReflectionTools.invoke( this.getService(), methodName, data );

            //Return ResponseEntity
            return this.createResponseEntitiy( entityResult );
        }
        catch( final Exception exception ){
            return this.createResponseEntityInternalServerError( exception );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| COPY & MOVE ENDPOINT |----------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PutMapping(
            path = "/{" + KEY_ENTITY + "}/sdms/copy",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityResult> sdmsCopy(
            @PathVariable( value = KEY_ENTITY, required = true ) final String entity,
            @RequestBody( required = true ) final OSdmsRestDataDto data
    ){

        CheckingTools.failIf(this.getService() == null, NullPointerException.class, MSG_SERVICE_IS_NULL );

        //Build the method name
        final String methodName = new StringBuffer()
                .append( entity )
                .append( METHOD_DMS_COPY )
                .toString();

        try {
            //Service Request
            final EntityResult entityResult = (EntityResult) ReflectionTools.invoke( this.getService(), methodName, data );

            //Return ResponseEntity
            return this.createResponseEntitiy( entityResult );
        }
        catch( final Exception exception ){
            return this.createResponseEntityInternalServerError( exception );
        }
    }

    @PutMapping(
            path = "/{" + KEY_ENTITY + "}/sdms/move",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityResult> sdmsMove(
            @PathVariable( value = KEY_ENTITY, required = true ) final String entity,
            @RequestBody( required = true ) final OSdmsRestDataDto data
    ){

        CheckingTools.failIf(this.getService() == null, NullPointerException.class, MSG_SERVICE_IS_NULL );

        //Build the method name
        final String methodName = new StringBuffer()
                .append( entity )
                .append( METHOD_DMS_MOVE )
                .toString();

        try {
            //Service Request
            final EntityResult entityResult = (EntityResult) ReflectionTools.invoke( this.getService(), methodName, data );

            //Return ResponseEntity
            return this.createResponseEntitiy( entityResult );
        }
        catch( final Exception exception ){
            return this.createResponseEntityInternalServerError( exception );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DELETE ENDPOINT |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @DeleteMapping(
            path = "/{" + KEY_ENTITY + "}/sdms/delete/id/{" + KEY_ID + "}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityResult> sdmsDeleteById(
            @PathVariable( value = KEY_ENTITY, required = true ) final String entity,
            @PathVariable( value = KEY_ID, required = true ) final Serializable id,
            @RequestParam( value = KEY_DATA, required = false ) final OSdmsRestDataDto data
    ){

        CheckingTools.failIf(this.getService() == null, NullPointerException.class, MSG_SERVICE_IS_NULL );

        //Build the method name
        final String methodName = new StringBuffer()
                .append( entity )
                .append( METHOD_DMS_DELETE_BY_ID )
                .toString();

        try {
            //Service Request
            final EntityResult entityResult = (EntityResult) ReflectionTools.invoke( this.getService(), methodName, id, data );

            //Return ResponseEntity
            return this.createResponseEntitiy( entityResult );
        }
        catch( final Exception exception ){
            return this.createResponseEntityInternalServerError( exception );
        }
    }



    @DeleteMapping(
            path = "/{" + KEY_ENTITY + "}/sdms/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityResult> sdmsDelete(
            @PathVariable( value = KEY_ENTITY, required = true ) final String entity,
            @RequestBody( required = true ) final OSdmsRestDataDto data
    ){

        CheckingTools.failIf(this.getService() == null, NullPointerException.class, MSG_SERVICE_IS_NULL );

        //Build the method name
        final String methodName = new StringBuffer()
                .append( entity )
                .append( METHOD_DMS_DELETE )
                .toString();

        try {
            //Service Request
            final EntityResult entityResult = (EntityResult) ReflectionTools.invoke( this.getService(), methodName, data );

            //Return ResponseEntity
            return this.createResponseEntitiy( entityResult );
        }
        catch( final Exception exception ){
            return this.createResponseEntityInternalServerError( exception );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| UTILITIES |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    protected ResponseEntity<EntityResult> createResponseEntityInternalServerError(final Exception exception ){
        LOGGER.error( null, exception );

        //Create EntityResult
        EntityResult entityResult = new EntityResultMapImpl( EntityResult.OPERATION_WRONG, EntityResult.BEST_COMPRESSION );
        entityResult.setMessage( exception.getMessage() );

        //Return the EntityResult with HTTP Status 500
        return new ResponseEntity<>( entityResult, HttpStatus.INTERNAL_SERVER_ERROR );
    }



    protected ResponseEntity createResponseEntitiy( final EntityResult entityResult ){
        //Initialize the result default
        ResponseEntity result = ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( entityResult );;

        //Check if there is a result
        if( entityResult.getCode() != EntityResult.OPERATION_WRONG ){ // If there is a result
            //Build the result
            result = ResponseEntity.ok().body( entityResult );
        }

        return result;
    }




    protected ResponseEntity createResponseEntityWithDocument(final EntityResult entityResult ){
        //Initialize the result default
        ResponseEntity result = ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( entityResult );

        //Init Data to return file
        String fileName = null;
        long fileSize = 0;
        InputStreamResource file = null;

        //Check if there is a result
        if( entityResult.getCode() != EntityResult.OPERATION_WRONG && entityResult.calculateRecordNumber() == 1 ){ // If there is a result
            //Get result
            final Map<String, Object> recordValues = entityResult.getRecordValues( 0 );

            //Get Data
            file = new InputStreamResource( (InputStream) recordValues.get( "file" ) );
            fileName = (String) recordValues.get( "name" );
            fileSize = (long) recordValues.get( "size" );

            if( fileSize != 0 ) {
                //Build the result
                result = ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=\"%s\"", fileName))
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize))
                        .body(file);
            }
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
