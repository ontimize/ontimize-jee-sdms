package com.ontimize.jee.sdms.engine.s3.util.input.filter.mapper;

import com.ontimize.jee.sdms.common.dto.OSdmsRestDataDto;
import com.ontimize.jee.sdms.engine.s3.util.input.OSdmsS3InputMapper;
import com.ontimize.jee.sdms.engine.s3.util.input.filter.OSdmsS3InputFilter;
import com.ontimize.jee.sdms.engine.s3.util.input.filter.field.OSdmsS3InputFilterField;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * Mapper class to map the {@link OSdmsS3InputFilter} from {@link OSdmsRestDataDto}.
 *
 * @see OSdmsS3InputMapper
 * @see OSdmsRestDataDto
 * @see OSdmsS3InputFilter
 */
@Component( "OSdmsS3InputFilterMapper" )
public class OSdmsS3InputFilterMapper extends OSdmsS3InputMapper<OSdmsS3InputFilter> {

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3InputFilter map( final OSdmsRestDataDto data ) {
        final OSdmsS3InputFilter result = new OSdmsS3InputFilter();
        if( data == null ) return result;

        final Map<String, Object> filterMap = data.getFilter();
        if( filterMap != null && ! filterMap.isEmpty() ) {
            result.setWorkspace( this.getString( OSdmsS3InputFilterField.WORKSPACE.getValue(), filterMap ) );
            result.setPrefixes( this.getListString( OSdmsS3InputFilterField.PREFIX.getValue(), filterMap ) );
            result.setFileNames( this.getListString( OSdmsS3InputFilterField.FILE_NAME.getValue(), filterMap ) );
            result.setIds( this.getListString( OSdmsS3InputFilterField.ID.getValue(), filterMap ) );
            result.setKeys( this.getListString( OSdmsS3InputFilterField.KEY.getValue(), filterMap ) );
            result.setData( this.getMapStringObject( OSdmsS3InputFilterField.DATA.getValue(), filterMap ) );
            result.setMaxKeys( this.getInteger( OSdmsS3InputFilterField.MAX_KEYS.getValue(), filterMap ) );
            result.setDelimiter( this.getString( OSdmsS3InputFilterField.DELIMITER.getValue(), filterMap ) );
            result.setMarker( this.getString( OSdmsS3InputFilterField.MARKER.getValue(), filterMap ) );
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
