package com.keshav.student_performance_tracker.repository;


import com.keshav.student_performance_tracker.model.Event;
import com.keshav.student_performance_tracker.model.Participation;
import com.keshav.student_performance_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findByStudent(User student);
    void deleteByStudentAndEvent(User student, Event event);
    Participation findByStudentAndEvent(User student, Event event);
    void deleteByEvent(Event event);
    boolean existsByEvent_Id(Long eventId);
}

