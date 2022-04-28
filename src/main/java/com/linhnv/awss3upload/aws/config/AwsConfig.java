package com.linhnv.awss3upload.aws.config;

import com.linhnv.awss3upload.aws.props.AwsProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class AwsConfig {
    @Autowired
    private AwsProps awsProps;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(awsProps.getCredentials().getRegion())
                .credentialsProvider(StaticCredentialsProvider.create(getCredentials()))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(awsProps.getCredentials().getRegion())
                .credentialsProvider(StaticCredentialsProvider.create(getCredentials()))
                .build();
    }

    private AwsCredentials getCredentials() {
        return AwsBasicCredentials.create
                (awsProps.getCredentials().getAccessKey(), awsProps.getCredentials().getSecretKey());
    }

}
