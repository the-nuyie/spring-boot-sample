package com.example.springbootsample.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.springbootsample.config.AWSConfig;
import com.example.springbootsample.config.StorageConfig;
import com.example.springbootsample.dto.FileStorageResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class AWSS3Service {

    private static final Logger LOG = LoggerFactory.getLogger(AWSS3Service.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private AWSConfig awsConfig;

    @Autowired
    private StorageConfig storageConfig;

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(storageConfig.getStorageTempDirectory()+multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            LOG.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
        }
        return file;
    }

    // @Async annotation ensures that the method is executed in a different thread

    @Async
    public S3ObjectInputStream findByName(String fileName) {
        LOG.info("Downloading file with name {}", fileName);
        return amazonS3.getObject(awsConfig.getBucketName(), fileName).getObjectContent();
    }

    public List<S3ObjectSummary> listObjects(){
        ObjectListing objectListing = amazonS3.listObjects(awsConfig.getBucketName());
        return objectListing.getObjectSummaries();
    }

    @Async
    public FileStorageResponse save(final MultipartFile multipartFile) {
        FileStorageResponse response = new FileStorageResponse();
        try {
            File file = convertMultiPartFileToFile(multipartFile);
            long size = multipartFile.getSize();
            String fileCode = RandomStringUtils.randomAlphanumeric(8);
            // final String fileName = LocalDateTime.now() + "_" + file.getName();
            String fileName = awsConfig.getBucketDirectory() + fileCode + "_" + file.getName();
            LOG.info("Uploading file with name {}", fileName);
            PutObjectRequest putObjectRequest = new PutObjectRequest(awsConfig.getBucketName(), fileName, file);
            amazonS3.putObject(putObjectRequest);

            LOG.info("Uploaded file with name {}", fileName);

            //1 Files.delete(file.toPath()); // Remove the file locally created in the project folder

            response.setFileName(fileName);
            response.setSize(size);
            response.setDownloadUri("/downloadFile/" + fileName);

            LOG.info("Set response done.");

        } catch (AmazonServiceException e) {
            LOG.error("Error {} occurred while uploading file", e.getLocalizedMessage());
        }
        //1 catch (IOException ex) {
        //1     LOG.error("Error {} occurred while deleting temporary file", ex.getLocalizedMessage());
        //1 }
        return response;
    }

}
