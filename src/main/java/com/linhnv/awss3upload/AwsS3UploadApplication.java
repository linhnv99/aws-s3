package com.linhnv.awss3upload;

import com.linhnv.awss3upload.aws.props.AwsProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
public class AwsS3UploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsS3UploadApplication.class, args);
        AwsProps a = new AwsProps();
    }
}
