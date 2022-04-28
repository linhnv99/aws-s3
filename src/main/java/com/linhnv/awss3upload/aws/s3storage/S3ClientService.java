package com.linhnv.awss3upload.aws.s3storage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public abstract class S3ClientService {

    /**
     * Create bucket
     */
    public abstract void createBucket();

    /**
     * Upload file to S3
     *
     * @param fileUpload
     * @param objectKey
     */
    public abstract void putObject(byte[] fileUpload, String objectKey);

    /**
     * Download an object
     *
     * @param objectKey
     */
    public abstract byte[] downloadObject(String objectKey);

    /**
     * Sign url to upload file
     *
     * @param objectKey
     * @return
     * @throws IOException
     */
    public abstract URL signUrl(String objectKey);

    public String transformObjectKey(String fileName) {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + "_" + fileName;
    }
}
