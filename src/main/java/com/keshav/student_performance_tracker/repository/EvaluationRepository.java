package com.keshav.student_performance_tracker.repository;

import com.keshav.student_performance_tracker.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}