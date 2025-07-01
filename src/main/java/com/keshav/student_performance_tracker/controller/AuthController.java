// src/main/java/com/keshav/student_performance_tracker/controller/AuthController.java
package com.keshav.student_performance_tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}