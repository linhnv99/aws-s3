package com.linhnv.awss3upload.controllers;

import com.linhnv.awss3upload.aws.s3storage.S3ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@RestController
public class HomeController {
    @Autowired
    private S3ClientService s3Service;

    @GetMapping("/create-bucket")
    public String createBucket() {
        s3Service.createBucket();
        return "OKE";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile fileUpload) throws IOException {
        s3Service.putObject(fileUpload.getBytes(), Objects.requireNonNull(fileUpload.getOriginalFilename()));
        return "OKE";
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam("key") String objectKey) {
        byte[] output = s3Service.downloadObject(objectKey);
        return ResponseEntity.ok()
                .contentType(MediaType.ALL)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + objectKey + "\"")
                .body(output);
    }

    @GetMapping("/sign-url")
    public ResponseEntity<URL> signUrl(@RequestParam("key") String objectKey) {
        return ResponseEntity.ok(s3Service.signUrl(objectKey));
    }

}
