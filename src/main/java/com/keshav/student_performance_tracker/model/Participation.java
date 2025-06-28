package com.keshav.student_performance_tracker.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "participations")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "participation", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Evaluation> evaluations;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private String status; // e.g., "REGISTERED", "COMPLETED"

    public Participation() {}

    public Participation(User student, Event event, String status) {
        this.student = student;
        this.event = event;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public User getStudent() { return student; }
    public Event getEvent() { return event; }
    public String getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setStudent(User student) { this.student = student; }
    public void setEvent(Event event) { this.event = event; }
    public void setStatus(String status) { this.status = status; }
}