package com.keshav.student_performance_tracker.service;


import com.keshav.student_performance_tracker.model.Event;
import com.keshav.student_performance_tracker.repository.EvaluationRepository;
import com.keshav.student_performance_tracker.repository.EventRepository;
import com.keshav.student_performance_tracker.repository.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setTitle(eventDetails.getTitle());
        event.setType(eventDetails.getType());
        event.setDescription(eventDetails.getDescription());
        return eventRepository.save(event);
    }

    @Transactional
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        evaluationRepository.deleteByParticipation_Event(event);
        participationRepository.deleteByEvent(event);

        eventRepository.delete(event);
    }
    public boolean hasParticipations(Long eventId) {
        // Returns true if any participation exists for the event
        return participationRepository.existsByEvent_Id(eventId);
    }
}