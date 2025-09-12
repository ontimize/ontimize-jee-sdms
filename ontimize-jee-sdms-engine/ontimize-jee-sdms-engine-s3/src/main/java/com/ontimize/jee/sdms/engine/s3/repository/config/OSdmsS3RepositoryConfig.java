package com.ontimize.jee.sdms.engine.s3.repository.config;

import com.amazonaws.ClientConfiguration;
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

    @Value( "${ontimize.sdms.s3.max-connections:100}" )
    private int maxConnections;

    @Value( "${ontimize.sdms.s3.timeout.connection:5000}" )
    private int connectionTimeout;

    @Value( "${ontimize.sdms.s3.timeout.socket:10000}" )
    private int socketTimeout;

    @Value( "${ontimize.sdms.s3.ttl-connection:60000}" )
    private long ttlConnection;

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

        ClientConfiguration clientConfig = new ClientConfiguration()
                .withMaxConnections(this.maxConnections) // Aumenta el número de conexiones simultáneas
                .withConnectionTimeout(this.connectionTimeout) // Tiempo máximo para establecer la conexión (ms)
                .withSocketTimeout(this.socketTimeout) // Tiempo máximo para leer datos del socket (ms)
                .withTcpKeepAlive(true) // Mantiene las conexiones abiertas
                .withUseExpectContinue(true) // Mejora rendimiento en PUTs grandes
                .withConnectionTTL(this.ttlConnection) // TTL de conexión, útil para liberar sockets antiguos
                .withMaxErrorRetry(3);

        //Configure and return AmazonS3 bean
        final AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withClientConfiguration( clientConfig )
                .withCredentials( new AWSStaticCredentialsProvider( awsCreds ));

        if( this.endpoint != null && ! this.endpoint.isEmpty() ) {
            builder.withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration( this.endpoint, this.region ) )
                    .withPathStyleAccessEnabled( true );
        }
        else {
            builder.withRegion( Regions.fromName( this.region ) );
        }

        return builder.build();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
