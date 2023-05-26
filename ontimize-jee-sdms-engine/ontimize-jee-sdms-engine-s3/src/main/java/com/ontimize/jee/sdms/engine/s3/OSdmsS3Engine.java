package com.ontimize.jee.sdms.engine.s3;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.sdms.common.command.handler.IOSdmsCommandHandler;
import com.ontimize.jee.sdms.engine.s3.util.input.OSdmsS3InputMapper;
import com.ontimize.jee.sdms.common.dto.OSdmsRestDataDto;
import com.ontimize.jee.sdms.common.engine.IOSdmsEngine;
import com.ontimize.jee.sdms.engine.s3.command.*;
import com.ontimize.jee.sdms.engine.s3.util.input.data.OSdmsS3InputData;
import com.ontimize.jee.sdms.engine.s3.util.input.filter.OSdmsS3InputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;



/**
 * Implementation of the {@link IOSdmsEngine} interface.
 *
 * @see IOSdmsEngine
 */
@Component( "OSdmsS3Engine" )
public class OSdmsS3Engine implements IOSdmsEngine {

    /** The OSdmsCommandHandler to run the commands */
    private @Autowired IOSdmsCommandHandler oSdmsCommandHandler;

    /** The OSdmsS3InputMapper to map the OSdmsRestDataDto to OSdmsS3InputFilter */
    @Qualifier( "OSdmsS3InputFilterMapper" )
    private @Autowired OSdmsS3InputMapper<OSdmsS3InputFilter> oSdmsS3InputFilterMapper;

    /** The OSdmsS3InputMapper to map the OSdmsRestDataDto to OSdmsS3InputData */
    @Qualifier( "OSdmsS3InputDataMapper" )
    private @Autowired OSdmsS3InputMapper<OSdmsS3InputData> oSdmsS3InputDataMapper;

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - FIND |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult findById( final Serializable id, final OSdmsRestDataDto data ) {
        final OSdmsS3InputFilter requestFilter = this.oSdmsS3InputFilterMapper.map( data );
        final OSdmsS3InputData requestData = this.oSdmsS3InputDataMapper.map( data );
        return this.oSdmsCommandHandler.run( new OSdmsS3FindByIdCommand( String.valueOf( id ), requestFilter, requestData ));
    }

    @Override
    public EntityResult find( final OSdmsRestDataDto data ) {
        final OSdmsS3InputFilter requestFilter = this.oSdmsS3InputFilterMapper.map( data );
        final OSdmsS3InputData requestData = this.oSdmsS3InputDataMapper.map( data );
        return this.oSdmsCommandHandler.run( new OSdmsS3FindCommand( requestFilter, requestData ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - DOWNLOAD |----------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult downloadById( final Serializable id, final OSdmsRestDataDto data ) {
        final OSdmsS3InputFilter requestFilter = this.oSdmsS3InputFilterMapper.map( data );
        final OSdmsS3InputData requestData = this.oSdmsS3InputDataMapper.map( data );
        return this.oSdmsCommandHandler.run( new OSdmsS3DownloadByIdCommand( String.valueOf( id ), requestFilter, requestData ));
    }

    @Override
    public EntityResult download( final OSdmsRestDataDto data ) {
        final OSdmsS3InputFilter requestFilter = this.oSdmsS3InputFilterMapper.map( data );
        final OSdmsS3InputData requestData = this.oSdmsS3InputDataMapper.map( data );
        return this.oSdmsCommandHandler.run( new OSdmsS3DownloadCommand( requestFilter, requestData ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - UPLOAD |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult upload(final OSdmsRestDataDto data, final MultipartFile file ) {
        final OSdmsS3InputFilter requestFilter = this.oSdmsS3InputFilterMapper.map( data );
        final OSdmsS3InputData requestData = this.oSdmsS3InputDataMapper.map( data );
        return this.oSdmsCommandHandler.run( new OSdmsS3UploadCommand( requestFilter, requestData, file ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - CREATE |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult create( final OSdmsRestDataDto data ) {
        final OSdmsS3InputFilter requestFilter = this.oSdmsS3InputFilterMapper.map( data );
        final OSdmsS3InputData requestData = this.oSdmsS3InputDataMapper.map( data );
        return this.oSdmsCommandHandler.run( new OSdmsS3CreateCommand( requestFilter, requestData ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - UPDATE |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult update( final OSdmsRestDataDto data ) {
        final OSdmsS3InputFilter requestFilter = this.oSdmsS3InputFilterMapper.map( data );
        final OSdmsS3InputData requestData = this.oSdmsS3InputDataMapper.map( data );
        return this.oSdmsCommandHandler.run( new OSdmsS3UpdateCommand( requestFilter, requestData ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - COPY |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult copy( final OSdmsRestDataDto data ) {
        final OSdmsS3InputFilter requestFilter = this.oSdmsS3InputFilterMapper.map( data );
        final OSdmsS3InputData requestData = this.oSdmsS3InputDataMapper.map( data );
        return this.oSdmsCommandHandler.run( new OSdmsS3CopyCommand( requestFilter, requestData ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - MOVE |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult move( final OSdmsRestDataDto data ) {
        final OSdmsS3InputFilter requestFilter = this.oSdmsS3InputFilterMapper.map( data );
        final OSdmsS3InputData requestData = this.oSdmsS3InputDataMapper.map( data );
        return this.oSdmsCommandHandler.run( new OSdmsS3MoveCommand( requestFilter, requestData ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - DELETE |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public EntityResult deleteById( final Serializable id, final OSdmsRestDataDto data ) {
        final OSdmsS3InputFilter requestFilter = this.oSdmsS3InputFilterMapper.map( data );
        final OSdmsS3InputData requestData = this.oSdmsS3InputDataMapper.map( data );
        return this.oSdmsCommandHandler.run( new OSdmsS3DeleteByIdCommand( String.valueOf( id ), requestFilter, requestData ));
    }

    @Override
    public EntityResult delete( final OSdmsRestDataDto data ) {
        final OSdmsS3InputFilter requestFilter = this.oSdmsS3InputFilterMapper.map( data );
        final OSdmsS3InputData requestData = this.oSdmsS3InputDataMapper.map( data );
        return this.oSdmsCommandHandler.run( new OSdmsS3DeleteCommand( requestFilter, requestData ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
}
