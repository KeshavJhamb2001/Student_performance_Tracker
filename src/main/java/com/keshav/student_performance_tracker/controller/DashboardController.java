// src/main/java/com/keshav/student_performance_tracker/controller/DashboardController.java
package com.keshav.student_performance_tracker.controller;

import com.keshav.student_performance_tracker.dto.ParticipationResponseDTO;
import com.keshav.student_performance_tracker.service.EvaluationService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import com.keshav.student_performance_tracker.model.Evaluation;
import com.keshav.student_performance_tracker.model.Event;
import com.keshav.student_performance_tracker.model.Participation;
import com.keshav.student_performance_tracker.model.User;
import com.keshav.student_performance_tracker.repository.EvaluationRepository;
import com.keshav.student_performance_tracker.repository.EventRepository;
import com.keshav.student_performance_tracker.repository.UserRepository;
import com.keshav.student_performance_tracker.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class DashboardController {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private UserRepository userRepository;
    // In your StudentDashboardController

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        if ("STUDENT".equalsIgnoreCase(user.getRole())) {
            return "student_dashboard";
        } else {
            return "teacher_dashboard";
        }
    }
    // src/main/java/com/keshav/student_performance_tracker/controller/DashboardController.java
    @GetMapping("/student/events")
    public String showAvailableEvents(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        List<Event> events = eventRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("events", events);
        return "student_events";
    }
/*
    @PostMapping("/student/join-event")
    public String joinEvent(@RequestParam Long eventId, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        participationService.participate(user.getId(), eventId);
        return "redirect:/student/registered-events";
    }
*/
@PostMapping("/student/join-event")
public String joinEvent(@RequestParam Long eventId, Authentication authentication, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
    String email = authentication.getName();
    User user = userRepository.findByEmail(email).orElse(null);
    try {
        participationService.participate(user.getId(), eventId);
        redirectAttributes.addFlashAttribute("successMessage", "Successfully registered for the event!");
    } catch (RuntimeException e) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/student/events";
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

    @GetMapping("/student/evaluation-report")
    public void downloadEvaluationReport(Authentication authentication, HttpServletResponse response) throws Exception {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            response.sendRedirect("/login");
            return;
        }
        List<Evaluation> evaluations = evaluationRepository.findByParticipation_Student_Id(user.getId());

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"evaluation_report.csv\"");

        PrintWriter writer = response.getWriter();
        // Add student name and ID at the top
        writer.println("Student Name:," + user.getName());
        writer.println("Student ID:," + user.getId());
        writer.println();
        writer.println("Event Name,Grade,Communication,Leadership,Creativity,Teamwork,Total Score");
        for (Evaluation eval : evaluations) {
            String eventName = eval.getParticipation().getEvent().getTitle();
            writer.println(String.join(",",
                    eventName,
                    eval.getGrade() != null ? eval.getGrade() : "",
                    String.valueOf(eval.getCommunication()),
                    String.valueOf(eval.getLeadership()),
                    String.valueOf(eval.getCreativity()),
                    String.valueOf(eval.getTeamwork()),
                    String.valueOf(eval.getTotalScore())
            ));
        }
        writer.flush();
        writer.close();
    }
    @GetMapping("/teacher/events")
    public String showAllEvents(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        List<Event> events = eventRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("events", events);
        return "teacher_events";
    }

    @GetMapping("/teacher/create-event")
    public String showCreateEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "create_event";
    }

    @PostMapping("/teacher/create-event")
    public String createEvent(@ModelAttribute Event event) {
        eventRepository.save(event);
        return "redirect:/teacher/events";
    }
    @PostMapping("/teacher/delete-event")
    public String deleteEvent(@RequestParam Long eventId) {
        eventRepository.deleteById(eventId);
        return "redirect:/teacher/events";
    }
    @GetMapping("/teacher/participations")
    public String showAllParticipations(Model model) {
        List<ParticipationResponseDTO> participations = participationService.getAllParticipationsWithStudentAndEvent();
        model.addAttribute("participations", participations);
        return "teacher_participations";
    }
    @PostMapping("/teacher/delete-participation")
    public String deleteParticipation(@RequestParam Long participationId) {
        participationService.deleteParticipationById(participationId);
        return "redirect:/teacher/participations";
    }
    @GetMapping("/teacher/evaluate/{participationId}")
    public String showEvaluationForm(@PathVariable Long participationId, Model model) {
        model.addAttribute("participationId", participationId);
        model.addAttribute("evaluation", new Evaluation());
        return "evaluate_student";
    }

    // src/main/java/com/keshav/student_performance_tracker/controller/DashboardController.java
    @PostMapping("/teacher/evaluate")
    public String evaluateStudent(@RequestParam Long participationId, @ModelAttribute Evaluation evaluation) {
        Optional<Evaluation> existing = evaluationRepository.findByParticipation_Id(participationId);
        if (existing.isPresent()) {
            evaluation.setId(existing.get().getId());
        }
        Participation participation = participationService.getParticipationById(participationId);
        evaluation.setParticipation(participation);
        evaluationRepository.save(evaluation);
        return "redirect:/teacher/participations";
    }
    @GetMapping("/teacher/update-evaluation/{id}")
    public String showUpdateEvaluationForm(@PathVariable Long id, Model model) {
        Evaluation evaluation = evaluationRepository.findById(id).orElse(null);
        model.addAttribute("evaluation", evaluation);
        return "update_evaluation";
    }

    @PostMapping("/teacher/update-evaluation")
    public String updateEvaluation(@ModelAttribute Evaluation evaluation) {
        evaluationRepository.save(evaluation);
        return "redirect:/teacher/participations";
    }
    @GetMapping("/teacher/evaluations")
    public String showAllEvaluations(Model model) {
        List<Evaluation> evaluations = evaluationRepository.findAll();
        model.addAttribute("evaluations", evaluations);
        return "teacher_evaluations";
    }
}