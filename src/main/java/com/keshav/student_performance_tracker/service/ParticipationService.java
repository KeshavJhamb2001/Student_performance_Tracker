package com.keshav.student_performance_tracker.service;



import com.keshav.student_performance_tracker.dto.ParticipationResponseDTO;
import com.keshav.student_performance_tracker.model.Event;
import com.keshav.student_performance_tracker.model.Participation;
import com.keshav.student_performance_tracker.model.User;
import com.keshav.student_performance_tracker.repository.EvaluationRepository;
import com.keshav.student_performance_tracker.repository.EventRepository;
import com.keshav.student_performance_tracker.repository.ParticipationRepository;
import com.keshav.student_performance_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;
    /*
    public Participation participate(Long studentId, Long eventId) {
        User student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        Participation participation = new Participation(student, event, "REGISTERED");
        return participationRepository.save(participation);
    }
    */

    public Participation participate(Long studentId, Long eventId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Check for existing participation
        Participation existing = participationRepository.findByStudentAndEvent(student, event);
        if (existing != null) {
            throw new RuntimeException("Student already registered for this event");
        }

        Participation participation = new Participation(student, event, "REGISTERED");
        return participationRepository.save(participation);
    }
    public List<ParticipationResponseDTO> getAllParticipationsWithStudentAndEvent() {
        List<Participation> participations = participationRepository.findAll();

        return participations.stream().map(p -> {
            String studentName = p.getStudent().getName();
            String eventName = p.getEvent().getTitle();
            return new ParticipationResponseDTO(
                    p.getId(),
                    p.getStatus(),
                    p.getEvent().getId(),
                    eventName,
                    p.getStudent().getId(),
                    studentName
            );
        }).collect(Collectors.toList());
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
    public void deleteParticipationById(Long id) {
        evaluationRepository.findByParticipation_Id(id)
                .ifPresent(evaluationRepository::delete);
        participationRepository.deleteById(id);
    }
    // src/main/java/com/keshav/student_performance_tracker/service/ParticipationService.java
    public Participation getParticipationById(Long id) {
        return participationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participation not found"));
    }

}

