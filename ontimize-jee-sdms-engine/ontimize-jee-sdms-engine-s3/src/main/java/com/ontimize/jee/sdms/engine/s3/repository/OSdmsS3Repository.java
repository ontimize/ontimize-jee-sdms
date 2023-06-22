package com.ontimize.jee.sdms.engine.s3.repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.ontimize.jee.sdms.engine.s3.repository.dto.OSdmsS3RepositoryDto;
import com.ontimize.jee.sdms.engine.s3.repository.request.OSdmsS3RepositorySimpleRequest;
import com.ontimize.jee.sdms.engine.s3.repository.response.OSdmsS3RepositoryResponse;
import com.ontimize.jee.sdms.engine.s3.repository.response.builder.IOSdmsS3RepositoryResponseBuilder;
import com.ontimize.jee.sdms.engine.s3.repository.response.codes.OSdmsS3RepositoryResponseCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Implementation of {@link IOSdmsS3Repository}. This class represents the repository, which will perform extra actions
 * to the S3 API.
 *
 * @see IOSdmsS3Repository
 */
@Repository( "OSdmsS3Repository" )
public class OSdmsS3Repository implements IOSdmsS3Repository {

    /**
     * The LOGGER constant, which is an instance of org.slf4j.Logger used for logging events and diagnostic messages
     * during program execution.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger( OSdmsS3Repository.class );

    //Messages
    private static final String MSG_ERROR_FIND = "An error occurred while performing the search";
    private static final String MSG_NOT_FOUND_FIND = "No results were found for the search";
    private static final String MSG_ERROR_DOWNLOAD = "An error occurred while performing the download";
    private static final String MSG_NOT_FOUND_DOWNLOAD = "No results were found for the download";
    private static final String MSG_ERROR_UPLOAD = "An error occurred while performing the upload";
    private static final String MSG_ERROR_COPY = "An error occurred while performing the copy";
    private static final String MSG_ERROR_DELETE = "An error occurred while performing the deletion";


    //Dependencies
    /** The Amazon S3 client to perform the operations with the S3 API. */
    private @Autowired AmazonS3 amazonS3;

    /** The s3 repository response builder to build the response of each operation. */
    private @Autowired IOSdmsS3RepositoryResponseBuilder oSdmsS3RepositoryResponseBuilder;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3Repository() {
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| FIND |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> find( final ListObjectsRequest request ) {
        OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> result = this.oSdmsS3RepositoryResponseBuilder
                .code( OSdmsS3RepositoryResponseCodes.ERROR ).message( MSG_ERROR_FIND ).build();

        final ObjectListing requestResult = this.amazonS3.listObjects( request );

        if( requestResult != null ) {
            final List<OSdmsS3RepositoryDto> data = new ArrayList<>();
            result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.NOT_FOUND ).message(
                    MSG_NOT_FOUND_FIND ).build();

            final List<S3ObjectSummary> objectSummaries = requestResult.getObjectSummaries();
            if( objectSummaries != null && ! objectSummaries.isEmpty() ) {
                final List<OSdmsS3RepositoryDto> files = objectSummaries.stream().map( target -> {
                    final ObjectMetadata objectMetadata = this.amazonS3.getObjectMetadata( target.getBucketName(),
                                                                                           target.getKey()
                                                                                         );
                    final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
                    dto.set( target );
                    dto.set( objectMetadata );
                    return dto;
                } ).collect( Collectors.toList() );
                data.addAll( files );
            }

            final List<String> commonPrefixes = requestResult.getCommonPrefixes();
            if( commonPrefixes != null && ! commonPrefixes.isEmpty() ) {
                final List<OSdmsS3RepositoryDto> folders = commonPrefixes.stream().map( target -> {
                    final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
                    dto.setFolderData( request.getBucketName(), target );
                    return dto;
                } ).collect( Collectors.toList() );
                data.addAll( folders );
            }

            if( ! data.isEmpty() ) {
                result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( data );
            }
        }

        return result;
    }


    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> find( final List<ListObjectsRequest> requests ) {
        final Set<OSdmsS3RepositoryDto> data = new HashSet<>();
        OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> result = this.oSdmsS3RepositoryResponseBuilder
                .code( OSdmsS3RepositoryResponseCodes.NOT_FOUND ).message( MSG_NOT_FOUND_FIND ).build();

        for( final ListObjectsRequest request : requests ) {
            result = this.find( request );

            if( result != null && result.getCode() == OSdmsS3RepositoryResponseCodes.OK ) {
                data.addAll( result.getData() );
            }
        }

        if( ! data.isEmpty() ) {
            result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( data );
        }

        return result;
    }


// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DOWNLOAD |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> download( final ListObjectsRequest request ) {
        final Set<OSdmsS3RepositoryDto> data = new HashSet<>();
        OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> result = this.oSdmsS3RepositoryResponseBuilder
                .code( OSdmsS3RepositoryResponseCodes.ERROR ).message( MSG_ERROR_DOWNLOAD ).build();

        final OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> findResponse = this.find( request );
        if( findResponse == null || findResponse.getCode() != OSdmsS3RepositoryResponseCodes.OK ) {
            return findResponse;
        }

        final List<OSdmsS3RepositoryDto> findResponseData = findResponse.getData();
        findResponseData.forEach( target -> {
            final GetObjectRequest getObjectRequest = new GetObjectRequest( target.getBucket(), target.getKey() );
            final S3Object s3Object = this.amazonS3.getObject( getObjectRequest );

            if( s3Object != null ) {
                final OSdmsS3RepositoryDto dto = new OSdmsS3RepositoryDto();
                dto.set( s3Object );
                data.add( dto );
            }
        } );

        if( ! data.isEmpty() ) {
            result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( data );
        }

        return result;
    }


    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> download( final List<ListObjectsRequest> requests ) {
        final Set<OSdmsS3RepositoryDto> data = new HashSet<>();
        OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> result = this.oSdmsS3RepositoryResponseBuilder
                .code( OSdmsS3RepositoryResponseCodes.NOT_FOUND ).message( MSG_NOT_FOUND_DOWNLOAD ).build();

        for( final ListObjectsRequest request : requests ) {
            result = this.download( request );

            if( result != null && result.getCode() == OSdmsS3RepositoryResponseCodes.OK ) {
                data.addAll( result.getData() );
            }
        }

        if( ! data.isEmpty() ) {
            result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( data );
        }

        return result;
    }


// ------------------------------------------------------------------------------------------------------------------ \\
// -------| UPLOAD |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Transactional
    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> upload( final PutObjectRequest request ) {
        OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> result = this.oSdmsS3RepositoryResponseBuilder
                .code( OSdmsS3RepositoryResponseCodes.ERROR ).message( MSG_ERROR_UPLOAD ).build();

        //Build the transfer manager of Amazon S3 to upload the file
        final TransferManager transferManager = TransferManagerBuilder.standard()
                .withS3Client( this.amazonS3 )
                .build();

        try {
            final Upload upload = transferManager.upload( request );
            upload.waitForCompletion();

            final ListObjectsRequest findRequest = new ListObjectsRequest()
                    .withBucketName( request.getBucketName() )
                    .withPrefix( request.getKey() )
                    .withMaxKeys( 1 )
                    .withDelimiter( "/" );
            final OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> findResponse = this.find( findRequest );

            if( findResponse != null && findResponse.getCode() == OSdmsS3RepositoryResponseCodes.OK ) {
                final OSdmsS3RepositoryDto dto = findResponse.getData().get( 0 );
                result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( dto );
            }
        }
        catch( final InterruptedException e ) {
            LOGGER.error( e.getMessage() );
            Thread.currentThread().interrupt();
            result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.ERROR ).message(
                    e.getMessage() ).build();
        }

        return result;
    }


    @Transactional
    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> upload( final List<PutObjectRequest> requests ) {
        final Set<OSdmsS3RepositoryDto> data = new HashSet<>();
        OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> result = this.oSdmsS3RepositoryResponseBuilder
                .code( OSdmsS3RepositoryResponseCodes.ERROR ).message( MSG_ERROR_UPLOAD ).build();

        for( final PutObjectRequest request : requests ) {
            result = this.upload( request );

            if( result != null && result.getCode() == OSdmsS3RepositoryResponseCodes.OK ) {
                data.addAll( result.getData() );
            }
        }

        if( ! data.isEmpty() ) {
            result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( data );
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| COPY |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Transactional
    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> copy( final ListObjectsRequest request, final String bucket, final String key ) {
        final Set<OSdmsS3RepositoryDto> data = new HashSet<>();
        OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> result = this.oSdmsS3RepositoryResponseBuilder
                .code( OSdmsS3RepositoryResponseCodes.ERROR ).message( MSG_ERROR_COPY ).build();

        final OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> findResponse = this.find( request );
        if( findResponse == null || findResponse.getCode() != OSdmsS3RepositoryResponseCodes.OK ) {
            return findResponse;
        }

        if( findResponse.getData().size() == 1 ) {
            final OSdmsS3RepositoryDto target = findResponse.getData().get( 0 );
            final CopyObjectRequest copyObjectRequest = new CopyObjectRequest( target.getBucket(), target.getKey(),
                                                                               bucket, key
            );
            if( target.getKey().equals( key ) ) return result;
            this.amazonS3.copyObject( copyObjectRequest );

            final ListObjectsRequest findRequest = new ListObjectsRequest()
                    .withBucketName( bucket )
                    .withPrefix( key )
                    .withMaxKeys( 1 )
                    .withDelimiter( "/" );
            final OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> findByIdResponse = this.find( findRequest );

            if( findByIdResponse != null && findByIdResponse.getCode() == OSdmsS3RepositoryResponseCodes.OK ) {
                final OSdmsS3RepositoryDto dto = findByIdResponse.getData().get( 0 );
                data.add( dto );
            }
        }

        if( ! data.isEmpty() ) {
            result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( data );
        }

        return result;
    }


    @Transactional
    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> copyAll( final List<ListObjectsRequest> requests, final String bucket, final String prefix, String currentPrefix ) {
        final Set<OSdmsS3RepositoryDto> data = new HashSet<>();
        OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> result = this.oSdmsS3RepositoryResponseBuilder
                .code( OSdmsS3RepositoryResponseCodes.ERROR ).message( MSG_ERROR_COPY ).build();

        final OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> findResponse = this.find( requests );
        if( findResponse == null || findResponse.getCode() != OSdmsS3RepositoryResponseCodes.OK ) {
            return findResponse;
        }

        if( findResponse.getData().size() > 0 ) {
            for( final OSdmsS3RepositoryDto target : findResponse.getData() ) {
                final String newKey = prefix
                        .concat( target.getPrefix().replaceAll( String.format( "^%s", currentPrefix ), "" ) )
                        .concat( target.getName() );
                if( target.getKey().equals( newKey ) ) return result;
                final CopyObjectRequest copyObjectRequest = new CopyObjectRequest( target.getBucket(), target.getKey(),
                                                                                   bucket, newKey
                );
                this.amazonS3.copyObject( copyObjectRequest );

                final ListObjectsRequest findRequest = new ListObjectsRequest()
                        .withBucketName( bucket )
                        .withPrefix( newKey )
                        .withMaxKeys( 1 )
                        .withDelimiter( "/" );
                final OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> findByIdResponse = this.find( findRequest );

                if( findByIdResponse != null && findByIdResponse.getCode() == OSdmsS3RepositoryResponseCodes.OK ) {
                    final OSdmsS3RepositoryDto dto = findByIdResponse.getData().get( 0 );
                    data.add( dto );
                }
            }
        }

        if( ! data.isEmpty() ) {
            result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( data );
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| MOVE |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Transactional
    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> move( final ListObjectsRequest request, final String bucket, final String key ) {
        final Set<OSdmsS3RepositoryDto> data = new HashSet<>();
        OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> result = this.oSdmsS3RepositoryResponseBuilder
                .code( OSdmsS3RepositoryResponseCodes.ERROR ).message( MSG_ERROR_COPY ).build();

        final OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> findResponse = this.find( request );
        if( findResponse == null || findResponse.getCode() != OSdmsS3RepositoryResponseCodes.OK ) {
            return findResponse;
        }

        if( findResponse.getData().size() == 1 ) {
            final OSdmsS3RepositoryDto target = findResponse.getData().get( 0 );
            if( target.getKey().equals( key ) ) return result;
            final CopyObjectRequest copyObjectRequest = new CopyObjectRequest( target.getBucket(), target.getKey(),
                                                                               bucket, key
            );
            this.amazonS3.copyObject( copyObjectRequest );

            final ListObjectsRequest findRequest = new ListObjectsRequest()
                    .withBucketName( bucket )
                    .withPrefix( key )
                    .withMaxKeys( 1 )
                    .withDelimiter( "/" );
            final OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> findByIdResponse = this.find( findRequest );

            if( findByIdResponse == null || findByIdResponse.getCode() != OSdmsS3RepositoryResponseCodes.OK ) {
                return findByIdResponse;
            }

            final OSdmsS3RepositoryDto dto = findByIdResponse.getData().get( 0 );
            result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( dto );

            final boolean isSameResource = dto.getBucket().equals( target.getBucket() ) && dto.getKey().equals(
                    target.getKey() );
            if( ! isSameResource ) {
                final ListObjectsRequest deleteRequest = new ListObjectsRequest()
                        .withBucketName( target.getBucket() )
                        .withPrefix( target.getKey() );
                final OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> deleteResponse = this.delete( deleteRequest );

                if( deleteResponse == null || deleteResponse.getCode() != OSdmsS3RepositoryResponseCodes.OK ) {
                    return deleteResponse;
                }
            }
            data.add( dto );
        }

        if( ! data.isEmpty() ) {
            result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( data );
        }

        return result;
    }


    @Transactional
    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> moveAll( final List<ListObjectsRequest> requests, final String bucket, final String prefix, String currentPrefix ) {
        final Set<OSdmsS3RepositoryDto> data = new HashSet<>();
        OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> result = this.oSdmsS3RepositoryResponseBuilder
                .code( OSdmsS3RepositoryResponseCodes.ERROR ).message( MSG_ERROR_COPY ).build();

        final OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> findResponse = this.find( requests );
        if( findResponse == null || findResponse.getCode() != OSdmsS3RepositoryResponseCodes.OK ) {
            return findResponse;
        }

        if( findResponse.getData().size() > 0 ) {
            for( final OSdmsS3RepositoryDto target : findResponse.getData() ) {
                final String newKey = prefix
                        .concat( target.getPrefix().replaceAll( String.format( "^%s", currentPrefix ), "" ) )
                        .concat( target.getName() );
                final CopyObjectRequest copyObjectRequest = new CopyObjectRequest( target.getBucket(), target.getKey(),
                                                                                   bucket, newKey
                );

                if( target.getKey().equals( newKey ) ) return result;
                this.amazonS3.copyObject( copyObjectRequest );

                final ListObjectsRequest findRequest = new ListObjectsRequest()
                        .withBucketName( bucket )
                        .withPrefix( newKey )
                        .withMaxKeys( 1 )
                        .withDelimiter( "/" );
                final OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> findByIdResponse = this.find( findRequest );

                if( findByIdResponse == null || findByIdResponse.getCode() != OSdmsS3RepositoryResponseCodes.OK ) {
                    return findByIdResponse;
                }

                final OSdmsS3RepositoryDto dto = findByIdResponse.getData().get( 0 );
                result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( dto );

                final boolean isSameResource = dto.getBucket().equals( target.getBucket() ) && dto.getKey().equals(
                        target.getKey() );
                if( ! isSameResource ) {
                    final ListObjectsRequest deleteRequest = new ListObjectsRequest()
                            .withBucketName( target.getBucket() )
                            .withPrefix( target.getKey() );
                    final OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> deleteResponse = this.delete( deleteRequest );

                    if( deleteResponse == null || deleteResponse.getCode() != OSdmsS3RepositoryResponseCodes.OK ) {
                        return deleteResponse;
                    }
                }
                data.add( dto );
            }
        }

        if( ! data.isEmpty() ) {
            result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( data );
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// -------| DELETE |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Transactional
    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> delete( final ListObjectsRequest request ) {
        final Set<OSdmsS3RepositoryDto> data = new HashSet<>();
        OSdmsS3RepositoryResponse result = this.oSdmsS3RepositoryResponseBuilder
                .code( OSdmsS3RepositoryResponseCodes.ERROR ).message( MSG_ERROR_DELETE ).build();

        final OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> findResponse = this.find( request );
        if( findResponse == null || findResponse.getCode() != OSdmsS3RepositoryResponseCodes.OK ) {
            return findResponse;
        }

        final List<OSdmsS3RepositoryDto> findResponseData = findResponse.getData();
        for( final OSdmsS3RepositoryDto target : findResponseData ) {
            this.amazonS3.deleteObject( target.getBucket(), target.getKey() );
            final boolean isScuccessful = ! this.amazonS3.doesObjectExist( target.getBucket(), target.getKey() );

            if( isScuccessful ) {
                data.add( target );
            }
        }

        if( ! data.isEmpty() ) {
            result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( data );
        }

        return result;
    }


    @Transactional
    @Override
    public OSdmsS3RepositoryResponse<OSdmsS3RepositoryDto> delete( final List<ListObjectsRequest> requests ) {
        final Set<OSdmsS3RepositoryDto> data = new HashSet<>();
        OSdmsS3RepositoryResponse result = this.oSdmsS3RepositoryResponseBuilder
                .code( OSdmsS3RepositoryResponseCodes.ERROR ).message( MSG_ERROR_DELETE ).build();

        for( final ListObjectsRequest request : requests ) {
            result = this.delete( request );

            if( result != null && result.getCode() == OSdmsS3RepositoryResponseCodes.OK ) {
                data.addAll( result.getData() );
            }
        }

        if( ! data.isEmpty() ) {
            result = this.oSdmsS3RepositoryResponseBuilder.code( OSdmsS3RepositoryResponseCodes.OK ).build( data );
        }

        return result;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
