package com.ontimize.jee.sdms.common.action;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.sdms.common.dto.OSdmsRestDataDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public interface IOSdmsAction {

    // ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - FIND |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Find an file or folder by id.
     *
     * @param id   The id of the file or folder
     * @param data The data to find the file or folder
     *
     * @return the entity result with the information of the file or folder
     *
     * @see Serializable
     * @see OSdmsRestDataDto
     */
    EntityResult findById( Serializable id, OSdmsRestDataDto data );


    /**
     * Find files or folders.
     *
     * @param data The data to find the files or folders
     *
     * @return the entity result with the information of the files or folders
     *
     * @see OSdmsRestDataDto
     */
    EntityResult find( OSdmsRestDataDto data );

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - DOWNLOAD |----------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Download a file or folder by id.
     *
     * @param id   The id of the file or folder
     * @param data The data to download the file or folder
     *
     * @return the entity result with the information of the file or folder
     *
     * @see Serializable
     * @see OSdmsRestDataDto
     */
    EntityResult downloadById( Serializable id, OSdmsRestDataDto data );


    /**
     * Download a file or folder.
     *
     * @param data The data to download the file or folder
     *
     * @return the entity result with the information of the file or folder
     *
     * @see OSdmsRestDataDto
     */
    EntityResult download( OSdmsRestDataDto data );

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - UPLOAD |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Upload a file.
     *
     * @param data The data to upload the file
     * @param file The file to upload
     *
     * @return the entity result with the information of the file
     *
     * @see OSdmsRestDataDto
     * @see MultipartFile
     */
    EntityResult upload( OSdmsRestDataDto data, MultipartFile file );

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - CREATE |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Create a folder.
     *
     * @param data The data to create the folder
     *
     * @return the entity result with the information of the folder
     *
     * @see OSdmsRestDataDto
     */
    EntityResult create( OSdmsRestDataDto data );

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - UPDATE |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Update a file or folder
     *
     * @param data The data to update the file or folder
     *
     * @return the entity result with the information of the file or folder
     *
     * @see OSdmsRestDataDto
     */
    EntityResult update( OSdmsRestDataDto data );

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - COPY |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Copy a file or folder.
     *
     * @param data The data to copy the file or folder
     *
     * @return the entity result with the information of the file or folder
     *
     * @see OSdmsRestDataDto
     */
    EntityResult copy( OSdmsRestDataDto data );

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - MOVE |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Move a file or folder.
     *
     * @param data The data to move the file or folder
     *
     * @return the entity result with the information of the file or folder
     *
     * @see OSdmsRestDataDto
     */
    EntityResult move( OSdmsRestDataDto data );

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DMS - DELETE |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Delete a file or folder by id.
     *
     * @param id   The id of the file or folder
     * @param data The data to delete the file or folder
     *
     * @return the entity result with the information of the file or folder
     *
     * @see Serializable
     * @see OSdmsRestDataDto
     */
    EntityResult deleteById( Serializable id, OSdmsRestDataDto data );


    /**
     * Delete a file or folder.
     *
     * @param data The data to delete the file or folder
     *
     * @return the entity result with the information of the file or folder
     *
     * @see OSdmsRestDataDto
     */
    EntityResult delete( OSdmsRestDataDto data );

// ------------------------------------------------------------------------------------------------------------------ \\
}
