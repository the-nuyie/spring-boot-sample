package com.example.springbootsample.controller;

import com.example.springbootsample.service.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aws/buckets/")
public class AWSController {

    @Autowired
    private AWSS3Service s3Service;

    @GetMapping("/health")
    public String getHealthCheck(){
        return "AWS Controller is ready.";
    }

    @PostMapping(value = "/{bucketName}")
    public void createBucket(@PathVariable String bucketName){
        s3Service.createS3Bucket(bucketName);
    }

    @GetMapping
    public List<String> listBuckets(){
        var buckets = s3Service.listBuckets();
        var names = buckets.stream().map(Bucket::getName).collect(Collectors.toList());
        return names;
    }

    @DeleteMapping(value = "/{bucketName}")
    public void deleteBucket(@PathVariable String bucketName){
        s3Service.deleteBucket(bucketName);
    }

    @PostMapping(value = "/{bucketName}/objects")
    public void createObject(@PathVariable String bucketName, @RequestBody BucketObjectRepresentaion representaion) throws IOException {
        s3Service.putObject(bucketName, representaion);
    }

    @GetMapping(value = "/{bucketName}/objects/{objectName}")
    public File downloadObject(@PathVariable String bucketName, @PathVariable String objectName) throws IOException {
        s3Service.downloadObject(bucketName, objectName);
        return new File("./" + objectName);
    }

    @PatchMapping(value = "/{bucketName}/objects/{objectName}/{bucketSource}")
    public void moveObject(@PathVariable String bucketName, @PathVariable String objectName, @PathVariable String bucketSource) throws IOException {
        s3Service.moveObject(bucketName, objectName, bucketSource);
    }

}

}
