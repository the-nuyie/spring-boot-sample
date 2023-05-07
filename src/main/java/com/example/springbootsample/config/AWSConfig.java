package com.example.springbootsample.config;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AWSConfig {


    @Value("${aws.s3.access.key.id}")
    private String accessKeyId;

    @Value("${aws.s3.access.key.secret}")
    private String accessKeySecret;

    @Value("${aws.s3.region.name}")
    private String s3RegionName;

    @Value("${aws.s3.endpoint}")
    private String s3EndPoint;

    @Bean
    public AmazonS3 getAmazonS3Client() {
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, accessKeySecret);
        // Get Amazon S3 client and return the S3 client object
        /*
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(s3RegionName)
                .build();
        */
        /* Nuy guess ProfileCredentialProvider() is reading ./aws at user's home in OS
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3EndPoint, s3RegionName))
                .withCredentials(new ProfileCredentialsProvider())
                .build();
        */
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3EndPoint, s3RegionName))
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}
