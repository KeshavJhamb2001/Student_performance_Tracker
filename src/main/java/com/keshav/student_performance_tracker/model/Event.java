package com.keshav.student_performance_tracker.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String type; // TECH, CULTURAL, COMMUNITY
    private String description;
private  String name;
    // Constructors
    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Participation> participations;

    public Event() {}

    public Event(String title, String type, String description) {
        this.title = title;
        this.type = type;
        this.description = description;
    }
    public String getName() {
        return this.name;
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
