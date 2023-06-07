package com.example.springbootsample.controller;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.springbootsample.service.AWSS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sample-api/v1/aws/s3")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AWSController {

    Logger logger = LoggerFactory.getLogger(AWSController.class);

    private static final String MESSAGE_1 = "Uploaded the file successfully";
    private static final String FILE_NAME = "fileName";

    @Autowired
    AWSS3Service s3Service;

    @GetMapping("/findByName")
    public ResponseEntity<Object> findByName(@RequestBody(required = false) Map<String, String> params) {

        logger.info("START, Params [params="+params+"]");
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.noCache())
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + params.get(FILE_NAME) + "\"")
                .body(new InputStreamResource(s3Service.findByName(params.get(FILE_NAME))));
    }

    @GetMapping(value = "/findAll")
    @ResponseBody
    public List<S3ObjectSummary> listAllObjects() {
        List<S3ObjectSummary> list = s3Service.listObjects();
        logger.info("  listAllObjects()="+list.toString());
        return list;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestParam("file") MultipartFile multipartFile) {
        s3Service.save(multipartFile);
        return new ResponseEntity<>(MESSAGE_1, HttpStatus.OK);
    }


    @GetMapping("/health")
    public String healthCheck(){
        return "Health is looking fine.";
    }
}
