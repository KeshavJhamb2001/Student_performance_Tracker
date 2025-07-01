// src/main/java/com/keshav/student_performance_tracker/controller/StudentController.java
package com.keshav.student_performance_tracker.controller;

import com.keshav.student_performance_tracker.model.*;
import com.keshav.student_performance_tracker.repository.*;
import com.keshav.student_performance_tracker.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ParticipationService participationService;
    @Autowired
    private EvaluationRepository evaluationRepository;
/*
    @GetMapping("/student/events")
    public String showAvailableEvents(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        List<Event> events = eventRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("events", events);
        return "student_events";
    }

    @PostMapping("/student/join-event")
    public String joinEvent(@RequestParam Long eventId, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        participationService.participate(user.getId(), eventId);
        return "redirect:/student/registered-events";
    }

    @GetMapping("/student/registered-events")
    public String showRegisteredEvents(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        List<Participation> participations = participationService.getParticipationsByStudent(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("participations", participations);
        return "student_registered_events";
    }

    @GetMapping("/student/evaluations")
    public String showEvaluations(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        List<Evaluation> evaluations = evaluationRepository.findByParticipation_Student_Id(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("evaluations", evaluations);
        return "student_evaluations";
    }
    */
}