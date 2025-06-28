package com.keshav.student_performance_tracker.controller;



import com.keshav.student_performance_tracker.model.User;
import com.keshav.student_performance_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

   /* @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }*/
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            userService.updatePassword(userOpt.get(), newPassword);
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }
    /*
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Optional<User> existing = userService.login(user.getEmail(), user.getPassword());
        return existing.isPresent() ? "Login successful!" : "Invalid credentials.";
    }

     */
}
