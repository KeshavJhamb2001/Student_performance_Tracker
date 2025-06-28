package com.keshav.student_performance_tracker.repository;

import com.keshav.student_performance_tracker.model.Evaluation;
import com.keshav.student_performance_tracker.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByParticipation_Student_Id(Long studentId);
    List<Evaluation> findByParticipation_Event_Id(Long eventId);
// src/main/java/com/keshav/student_performance_tracker/repository/EvaluationRepository.java


    Optional<Evaluation> findByParticipation_Id(Long participationId);
    void deleteByParticipation_Event(Event event);

}