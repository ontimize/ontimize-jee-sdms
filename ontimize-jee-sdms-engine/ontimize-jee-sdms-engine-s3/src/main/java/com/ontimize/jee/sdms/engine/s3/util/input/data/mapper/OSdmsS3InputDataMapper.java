package com.ontimize.jee.sdms.engine.s3.util.input.data.mapper;

import com.ontimize.jee.sdms.common.dto.OSdmsRestDataDto;
import com.ontimize.jee.sdms.engine.s3.util.input.OSdmsS3InputMapper;
import com.ontimize.jee.sdms.engine.s3.util.input.data.OSdmsS3InputData;
import com.ontimize.jee.sdms.engine.s3.util.input.data.field.OSdmsS3InputDataField;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * Mapper for {@link OSdmsS3InputData} from {@link OSdmsRestDataDto}.
 *
 * @see OSdmsS3InputMapper
 * @see OSdmsRestDataDto
 * @see OSdmsS3InputData
 */
@Component( "OSdmsS3InputDataMapper" )
public class OSdmsS3InputDataMapper extends OSdmsS3InputMapper<OSdmsS3InputData> {

// ------------------------------------------------------------------------------------------------------------------ \\
// ------| IMPLEMENTED METHODS |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3InputData map( final OSdmsRestDataDto data ) {
        final OSdmsS3InputData result = new OSdmsS3InputData();
        if( data == null ) return result;

        final Map<String, Object> dataMap = data.getData();
        if( dataMap != null && ! dataMap.isEmpty() ) {
            result.setCurrentPrefix( this.getString( OSdmsS3InputDataField.CURRENT_PREFIX.getValue(), dataMap ) );
            result.setPrefix( this.getString( OSdmsS3InputDataField.PREFIX.getValue(), dataMap ) );
            result.setFileName( this.getString( OSdmsS3InputDataField.FILE_NAME.getValue(), dataMap ) );
            result.setId( this.getString( OSdmsS3InputDataField.ID.getValue(), dataMap ) );
            result.setKey( this.getString( OSdmsS3InputDataField.KEY.getValue(), dataMap ) );
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
