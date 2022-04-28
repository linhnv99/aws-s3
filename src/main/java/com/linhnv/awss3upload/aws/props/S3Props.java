package com.linhnv.awss3upload.aws.props;

import lombok.Data;

@Data
public class S3Props {
    private String bucketName;
    private String prefix;
}
