package com.linhnv.awss3upload.aws.props;

import lombok.Data;
import software.amazon.awssdk.regions.Region;

@Data
public class Credentials {
    private String accessKey;
    private String secretKey;
    private Region region;
}
