package com.kyterescue.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String landing() {
        System.out.println("Inside landing HomeController method");
        return "landing/landing";
    }
}
