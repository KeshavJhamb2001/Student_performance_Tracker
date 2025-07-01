package com.keshav.student_performance_tracker.controller;



import com.keshav.student_performance_tracker.model.User;
import com.keshav.student_performance_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeacherController {

    @Autowired
    private UserService userService;

    @GetMapping("/teacher/dashboard")
    public String showTeacherDashboard(Model model, Authentication authentication) {
        String email;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        User user = userService.findByEmail(email).orElse(null);
        model.addAttribute("user", user);
        return "teacher_dashboard";
    }
}