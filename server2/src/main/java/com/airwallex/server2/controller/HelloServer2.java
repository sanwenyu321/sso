package com.airwallex.server2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloServer2 {

    @RequestMapping("/hello")
    public String hello() {
        return "hello server2 to you";
    }
}
