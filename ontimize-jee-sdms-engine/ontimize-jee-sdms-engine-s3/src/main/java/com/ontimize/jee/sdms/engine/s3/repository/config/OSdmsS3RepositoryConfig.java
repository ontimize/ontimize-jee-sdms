package com.ontimize.jee.sdms.engine.s3.repository.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ontimize.jee.sdms.engine.s3.repository.config.condition.OSdmsS3RepositoryConfigCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;



/**
 * AmazonS3 Bean configuration class
 */
@Configuration
@Conditional( OSdmsS3RepositoryConfigCondition.class )
public class OSdmsS3RepositoryConfig {

    /** The access key from Amazon S3 */
    @Value( "${ontimize.sdms.s3.access-key}" )
    private String accessKey;


    /** The secret key from Amazon S3 */
    @Value( "${ontimize.sdms.s3.secret-key}" )
    private String secretKey;


    /** The region of Amazon S3 */
    @Value( "${ontimize.sdms.s3.region}" )
    private String region;


    /** The endpoing of Amazon S3 */
    @Value( "${ontimize.sdms.s3.endpoint:}" )
    private String endpoint;

// ------------------------------------------------------------------------------------------------------------------ \\

    public OSdmsS3RepositoryConfig() {}

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Create and configure the AmazonS3 Bean
     *
     * @return The AmazonS3 Bean
     */
    @Bean( "AmazonS3" )
    public AmazonS3 amazonS3() {
        //Initialise AWS credentials
        BasicAWSCredentials awsCreds = new BasicAWSCredentials( this.accessKey, this.secretKey );

        //Configure and return AmazonS3 bean
        final AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withCredentials( new AWSStaticCredentialsProvider( awsCreds ) );

        if( this.endpoint != null && !this.endpoint.isEmpty() ){
            builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(this.endpoint, this.region))
                    .withPathStyleAccessEnabled(true);
        }
        else {
            builder.withRegion(Regions.fromName(this.region));
        }

        return builder.build();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
