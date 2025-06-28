package com.keshav.student_performance_tracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "evaluations")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "participation_id")
    private Participation participation;
  /*  @OneToOne
    @JoinColumn(name = "participation_id")*/

    private String grade;
    private int communication;
    private int leadership;
    private int creativity;
    private int teamwork;
    private int totalScore;

    public Evaluation() {}

    public Evaluation(Participation participation, int communication, int leadership, int creativity, int teamwork) {
        this.participation = participation;
        this.communication = communication;
        this.leadership = leadership;
        this.creativity = creativity;
        this.teamwork = teamwork;
        recalculateTotal();
    }

    // Getters
    public Long getId() { return id; }
    public Participation getParticipation() { return participation; }
    public int getCommunication() { return communication; }
    public int getLeadership() { return leadership; }
    public int getCreativity() { return creativity; }
    public int getTeamwork() { return teamwork; }
    public int getTotalScore() { return totalScore; }
    public String getGrade() { return grade; }

    // Setters
    public void setGrade(String grade) { this.grade = grade; }
    public void setId(Long id) { this.id = id; }
    public void setParticipation(Participation participation) { this.participation = participation; }

    public void setCommunication(int communication) {
        this.communication = communication;
        recalculateTotal();
    }
    public void setLeadership(int leadership) {
        this.leadership = leadership;
        recalculateTotal();
    }
    public void setCreativity(int creativity) {
        this.creativity = creativity;
        recalculateTotal();
    }
    public void setTeamwork(int teamwork) {
        this.teamwork = teamwork;
        recalculateTotal();
    }
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    private void recalculateTotal() {
        this.totalScore = this.communication + this.leadership + this.creativity + this.teamwork;
    }
}