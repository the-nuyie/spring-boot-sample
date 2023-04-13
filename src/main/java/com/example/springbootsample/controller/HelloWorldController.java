package com.example.springbootsample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @GetMapping("/message")
    public String getWelcomeMessage(){
        return "Hello my friend.";
    }
}
