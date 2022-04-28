package com.linhnv.awss3upload.aws.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "aws")
@Data
public class AwsProps {

    private Credentials credentials;

    private Map<String, S3Props> s3;
}
