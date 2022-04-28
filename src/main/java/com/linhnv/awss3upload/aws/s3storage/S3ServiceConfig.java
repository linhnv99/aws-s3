package com.linhnv.awss3upload.aws.s3storage;

import com.linhnv.awss3upload.aws.props.AwsProps;
import com.linhnv.awss3upload.aws.props.S3Props;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3ServiceConfig {

    @Autowired
    private AwsProps awsProps;

    /**
     * Default
     *
     * @param s3Client
     * @return
     */
    @Bean
    public S3Service s3Service(S3Client s3Client, S3Presigner s3Presigner) {
        S3Props s3Props = awsProps.getS3().get("default");
        return S3Service.builder()
                .s3Client(s3Client)
                .s3Presigner(s3Presigner)
                .bucketName(s3Props.getBucketName())
                .prefix(s3Props.getPrefix())
                .build();
    }

    @Bean
    public S3Service s3AvatarUploadService(S3Client s3Client, S3Presigner s3Presigner) {
        S3Props s3Props = awsProps.getS3().get("avatar");
        return S3Service.builder()
                .s3Client(s3Client)
                .s3Presigner(s3Presigner)
                .bucketName(s3Props.getBucketName())
                .prefix(s3Props.getPrefix())
                .build();
    }

    @Bean
    public S3Service s3ProductUploadService(S3Client s3Client, S3Presigner s3Presigner) {
        S3Props s3Props = awsProps.getS3().get("product");
        return S3Service.builder()
                .s3Client(s3Client)
                .s3Presigner(s3Presigner)
                .bucketName(s3Props.getBucketName())
                .prefix(s3Props.getPrefix())
                .build();
    }
}
