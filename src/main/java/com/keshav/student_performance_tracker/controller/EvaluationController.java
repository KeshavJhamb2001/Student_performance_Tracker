package com.keshav.student_performance_tracker.controller;


import com.keshav.student_performance_tracker.model.Evaluation;
import com.keshav.student_performance_tracker.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/evaluate")
    public Evaluation evaluateStudent(@RequestParam Long participationId, @RequestBody Evaluation evaluationData) {
        return evaluationService.evaluateStudent(participationId, evaluationData);
    }
    @GetMapping("/participation/{participationId}")
    public Evaluation getEvaluationByParticipation(@PathVariable Long participationId) {
        return evaluationService.getEvaluationByParticipation(participationId);
    }
    @PutMapping("/update/{id}")
    public Evaluation updateEvaluation(@PathVariable Long id, @RequestBody Evaluation updatedData) {
        return evaluationService.updateEvaluation(id, updatedData);
    }
}