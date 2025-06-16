package com.keshav.student_performance_tracker.service;



import com.keshav.student_performance_tracker.model.Event;
import com.keshav.student_performance_tracker.model.Participation;
import com.keshav.student_performance_tracker.model.User;
import com.keshav.student_performance_tracker.repository.EventRepository;
import com.keshav.student_performance_tracker.repository.ParticipationRepository;
import com.keshav.student_performance_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public Participation participate(Long studentId, Long eventId) {
        User student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        Participation participation = new Participation(student, event, "REGISTERED");
        return participationRepository.save(participation);
    }

    public List<Participation> getParticipationsByStudent(Long studentId) {
        User student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        return participationRepository.findByStudent(student);
    }
    public String removeStudentFromEvent(Long studentId, Long eventId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Participation participation = participationRepository.findByStudentAndEvent(student, event);
        if (participation == null) {
            return "Participation not found";
        }

        participationRepository.delete(participation);
        return "Student removed from event successfully";
    }

}

