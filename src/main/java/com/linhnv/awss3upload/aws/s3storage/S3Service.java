package com.linhnv.awss3upload.aws.s3storage;

import lombok.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;


@Builder
public class S3Service extends S3ClientService {

    private final Logger logger = LogManager.getLogger(getClass());

    private final S3Client s3Client;

    private final S3Presigner s3Presigner;

    private final String bucketName;

    private final String prefix;

    private final String S3_PATH_SEPARATOR = "/";

    private String getPrefix() {
        return prefix != null && !prefix.isEmpty() ? prefix + S3_PATH_SEPARATOR : "";
    }

    @Override
    public void createBucket() {
        try {
            CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            s3Client.createBucket(bucketRequest);
            logger.info(bucketName + " is ready");
        } catch (S3Exception e) {
            logger.error("ERROR CREATE BUCKET: " + e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    @Override
    public void putObject(byte[] fileUpload, String objectKey) {
        try {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(getPrefix() + transformObjectKey(objectKey.replace(getPrefix(), "")))
//                    .acl("public-read")
                    .build();

            s3Client.putObject(objectRequest, RequestBody.fromBytes(fileUpload));
        } catch (S3Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] downloadObject(String objectKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(getPrefix() + objectKey.replace(getPrefix(), ""))
                .build();

        ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(getObjectRequest);
        return response.asByteArray();
    }

    @Override
    public URL signUrl(String objectKey) {
        try {
            AwsRequestOverrideConfiguration override = AwsRequestOverrideConfiguration.builder()
                    .putRawQueryParameter("x-amz-acl", "authenticated-read")
                    .build();

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(getPrefix() + objectKey.replace(getPrefix(), ""))
                    .expires(Instant.ofEpochSecond(1000))
                    .overrideConfiguration(override)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .putObjectRequest(objectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
            // Upload content to the Amazon S3 bucket by using this URL
            String myURL = presignedRequest.url().toString();

            logger.info(String.format("Presigned URL to upload a file to: %s with method: %s",
                    myURL, presignedRequest.httpRequest().method()));

            return presignedRequest.url();
        } catch (S3Exception e) {
            throw e;
        }
    }
}
