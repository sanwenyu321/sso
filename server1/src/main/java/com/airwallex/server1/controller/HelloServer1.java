package com.airwallex.server1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloServer1 {

    @RequestMapping("/hello")
    public String hello() {
        return "hello server1 to you";
    }
}
