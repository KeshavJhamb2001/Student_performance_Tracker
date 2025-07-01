package com.keshav.student_performance_tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Student Performance Tracker");
        return "index";
    }
}