package com.yoongspace.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accesspage")
public class PageAuthController {
    @GetMapping
    public String pageaccess() {
        return "Allow";
    }
}
