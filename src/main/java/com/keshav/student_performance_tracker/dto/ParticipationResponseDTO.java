package com.keshav.student_performance_tracker.dto;

public class ParticipationResponseDTO {
    private Long id;
    private String status;
    private Long eventId;
    private String eventName;
    private Long studentId;
    private String studentName;

    public ParticipationResponseDTO(Long id, String status, Long eventId, String eventName, Long studentId, String studentName) {
        this.id = id;
        this.status = status;
        this.eventId = eventId;
        this.eventName = eventName;
        this.studentId = studentId;
        this.studentName = studentName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getStatus() { return status; }
    public Long getEventId() { return eventId; }
    public String getEventName() { return eventName; }
    public Long getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }

    public void setId(Long id) { this.id = id; }
    public void setStatus(String status) { this.status = status; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
}
