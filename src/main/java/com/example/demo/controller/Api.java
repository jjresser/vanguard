package com.example.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Api {
    @GetMapping("/callHello") //http://localhost:8080/callHello
    public String hello(){
        return "hello";
    }
}
