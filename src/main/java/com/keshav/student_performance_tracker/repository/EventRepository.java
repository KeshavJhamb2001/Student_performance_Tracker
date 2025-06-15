package com.keshav.student_performance_tracker.repository;



import com.keshav.student_performance_tracker.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}