package com.kyterescue.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String viewLanding() {
        return "landing/landing";
    }

    @GetMapping("/about")
    public String viewAboutUs() {
        return "home/about";
    }
}
