package com.example.PrintAppPOC.Configurations;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
    @Value("${aws.access.key.id}")
    private String accessKeyId;
    @Value("${aws.secret.access.key}")
    private String accessKeySecret;
    @Value("${aws.s3.region}")
    private String awsS3Region = "ap-south-1";

    @Bean
    AmazonS3 s3(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId,accessKeySecret);
        return AmazonS3ClientBuilder
                .standard().withRegion(awsS3Region).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
    }
}
