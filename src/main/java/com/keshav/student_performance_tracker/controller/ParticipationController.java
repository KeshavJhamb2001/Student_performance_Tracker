package com.keshav.student_performance_tracker.controller;

import com.keshav.student_performance_tracker.dto.ParticipationResponseDTO;
import com.keshav.student_performance_tracker.model.Participation;
import com.keshav.student_performance_tracker.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participations")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @PostMapping("/join")
    public Participation participate(@RequestParam Long studentId, @RequestParam Long eventId) {
        return participationService.participate(studentId, eventId);
    }
    @GetMapping("/with-details")
    public ResponseEntity<List<ParticipationResponseDTO>> getAllWithStudentAndEventNames() {
        return ResponseEntity.ok(participationService.getAllParticipationsWithStudentAndEvent());
    }


    @GetMapping("/student/{studentId}")
    public List<Participation> getParticipationsByStudent(@PathVariable Long studentId) {
        return participationService.getParticipationsByStudent(studentId);
    }
    @DeleteMapping("/remove")
    public String removeParticipation(@RequestParam Long studentId, @RequestParam Long eventId) {
        return participationService.removeStudentFromEvent(studentId, eventId);
    }

}