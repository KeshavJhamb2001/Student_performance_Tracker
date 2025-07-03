package com.keshav.student_performance_tracker.service;

import com.keshav.student_performance_tracker.model.Evaluation;
import com.keshav.student_performance_tracker.model.Participation;
import com.keshav.student_performance_tracker.repository.EvaluationRepository;
import com.keshav.student_performance_tracker.repository.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    public Evaluation evaluateStudent(Long participationId, Evaluation evaluationData) {
        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new RuntimeException("Participation not found"));

        Evaluation evaluation = new Evaluation();
        evaluation.setParticipation(participation);
        evaluation.setCommunication(evaluationData.getCommunication());
        evaluation.setLeadership(evaluationData.getLeadership());
        evaluation.setCreativity(evaluationData.getCreativity());
        evaluation.setTeamwork(evaluationData.getTeamwork());

        int total = evaluationData.getCommunication() +
                evaluationData.getLeadership() +
                evaluationData.getCreativity() +
                evaluationData.getTeamwork();

        evaluation.setTotalScore(total);
        evaluation.setGrade(calculateGrade(total)); // SET GRADE

        return evaluationRepository.save(evaluation);
    }
    public Evaluation getEvaluationByParticipation(Long participationId) {
        return evaluationRepository.findAll().stream()
                .filter(e -> e.getParticipation().getId().equals(participationId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Evaluation not found for this participation"));
    }
    public Evaluation updateEvaluation(Long id, Evaluation updatedData) {
        Evaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found"));

        evaluation.setCommunication(updatedData.getCommunication());
        evaluation.setLeadership(updatedData.getLeadership());
        evaluation.setCreativity(updatedData.getCreativity());
        evaluation.setTeamwork(updatedData.getTeamwork());

        int total = updatedData.getCommunication() +
                updatedData.getLeadership() +
                updatedData.getCreativity() +
                updatedData.getTeamwork();

        evaluation.setTotalScore(total);
        evaluation.setGrade(calculateGrade(total));

        return evaluationRepository.save(evaluation);
    }
    public List<Evaluation> getEvaluationsByStudentId(Long studentId) {
        return evaluationRepository.findByParticipation_Student_Id(studentId);
    }
    public List<Evaluation> getEvaluationsByEventId(Long eventId) {
        return evaluationRepository.findByParticipation_Event_Id(eventId);
    }


    private String calculateGrade(int total) {
        if (total >= 17) return "A+";
        else if (total >= 14) return "A";
        else if (total >= 12) return "B+";
        else return "B";
    }
    // In EvaluationService.java

}