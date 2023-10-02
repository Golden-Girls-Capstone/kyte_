package com.kyterescue.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String landing() {
        return "landing/landing";
    }

    @GetMapping("/about")
    public String aboutUs() {
        return "home/about";
    }
}
