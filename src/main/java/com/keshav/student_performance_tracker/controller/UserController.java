package com.keshav.student_performance_tracker.controller;



import com.keshav.student_performance_tracker.model.User;
import com.keshav.student_performance_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Optional<User> existing = userService.login(user.getEmail(), user.getPassword());
        return existing.isPresent() ? "Login successful!" : "Invalid credentials.";
    }
}
