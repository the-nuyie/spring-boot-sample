package com.example.springbootsample.config;


import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AWSConfig {

    Logger logger = LoggerFactory.getLogger(AWSConfig.class);

    @Value("${aws.s3.access.key.id}")
    private String accessKeyId;

    @Value("${aws.s3.access.key.secret}")
    private String accessKeySecret;

    @Value("${aws.s3.access.session.token}")
    private String sessionToken;

    @Value("${aws.s3.region.name}")
    private String s3RegionName;

    @Value("${aws.s3.endpoint}")
    private String s3EndPoint;

    @Value("${aws.s3.access.mode}")
    private String accessMode;

    @Value("${https.proxy.host}")
    private String proxyHost;

    @Value("${https.proxy.port}")
    private int proxyPort;

    @Value("${http.nonProxy.host}")
    private String nonProxyHost;


    @Bean
    public AmazonS3 getAmazonS3Client() {
        /*
        ClientConfiguration config = new ClientConfiguration();
        config.setProtocol(Protocol.HTTPS);
        config.setProxyHost(proxyHost);
        config.setProxyPort(proxyPort);
        config.setNonProxyHosts(nonProxyHost);
        */

        AmazonS3 builder = null;
        if ("PROFILE".equals(accessMode)) {
            logger.info(">>N>> Connecting by ProfileCredential.");
            // ### Method 1 : Use /.aws
            // Nuy guess ProfileCredentialProvider() is reading /.aws at user's home in OS
            if(s3EndPoint != null && !"".equals(s3EndPoint)){
                builder = AmazonS3ClientBuilder
                        .standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3EndPoint, s3RegionName))
                        .withCredentials(new ProfileCredentialsProvider())
                        .build();
            }else{
                builder = AmazonS3ClientBuilder
                        .standard()
                        .withRegion(s3RegionName)
                        .withCredentials(new ProfileCredentialsProvider())
                        .build();
            }

        } else if ("BASIC".equals(accessMode)) {

            // ### Method 2 : Use value in application.properties
            if (sessionToken != null && !"".equals(sessionToken)) {
                logger.info(">>N>> Connecting by BasicCredential.");
                logger.info(">>N>> AccessKey="+accessKeyId+", Secret="+accessKeySecret+", SessionToken="+sessionToken);
                logger.info(">>N>> EndPoint="+s3EndPoint+", Region="+s3RegionName);
                final BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(accessKeyId, accessKeySecret, sessionToken);
                if(s3EndPoint != null && !"".equals(s3EndPoint)){
                    builder = AmazonS3ClientBuilder
                            .standard()
                            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3EndPoint, s3RegionName))
                            .withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials))
                            .build();
                }else{
                    builder = AmazonS3ClientBuilder
                            .standard()
                            // .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3EndPoint, s3RegionName))
                            .withRegion(s3RegionName)
                            .withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials))
                            .build();
                }

            } else {
                logger.info(">>N>> Connecting by BasicAWSCredentials.");
                logger.info(">>N>> AccessKey="+accessKeyId+", Secret="+accessKeySecret);
                logger.info(">>N>> EndPoint="+s3EndPoint+", Region="+s3RegionName);
                final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, accessKeySecret);
                if(s3EndPoint != null && !"".equals(s3EndPoint)){
                    builder = AmazonS3ClientBuilder
                            .standard()
                            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3EndPoint, s3RegionName))
                            // .withRegion(s3RegionName)
                            .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                            .build();
                }else{
                    builder = AmazonS3ClientBuilder
                            .standard()
                            // .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3EndPoint, s3RegionName))
                            .withRegion(s3RegionName)
                            .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                            .build();
                }

            }
        }
        return builder;
    }
}
