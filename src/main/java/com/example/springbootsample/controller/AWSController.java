package com.example.springbootsample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aws")
public class AWSController {

    @GetMapping("/health")
    public String getHealthCheck(){
        return "AWS Controller is ready.";
    }
}
