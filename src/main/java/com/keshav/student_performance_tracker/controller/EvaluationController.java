package com.keshav.student_performance_tracker.controller;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import com.keshav.student_performance_tracker.model.Evaluation;
import com.keshav.student_performance_tracker.service.EvaluationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/evaluate")
    public Evaluation evaluateStudent(@RequestParam Long participationId, @RequestBody Evaluation evaluationData) {
        return evaluationService.evaluateStudent(participationId, evaluationData);
    }
    @GetMapping("/participation/{participationId}")
    public Evaluation getEvaluationByParticipation(@PathVariable Long participationId) {
        return evaluationService.getEvaluationByParticipation(participationId);
    }
    @PutMapping("/update/{id}")
    public Evaluation updateEvaluation(@PathVariable Long id, @RequestBody Evaluation updatedData) {
        return evaluationService.updateEvaluation(id, updatedData);
    }
    @GetMapping("/student/{studentId}")
    public List<Evaluation> getEvaluationsByStudent(@PathVariable Long studentId) {
        return evaluationService.getEvaluationsByStudentId(studentId);
    }
    @GetMapping("/event/{eventId}")
    public List<Evaluation> getEvaluationsByEvent(@PathVariable Long eventId) {
        return evaluationService.getEvaluationsByEventId(eventId);
    }
    @GetMapping("/export/pdf/student/{studentId}")
    public void exportStudentEvaluationsToPdf(@PathVariable Long studentId, HttpServletResponse response) throws IOException {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByStudentId(studentId);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=student_" + studentId + "_evaluations.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Evaluation Report for Student ID: " + studentId).setBold().setFontSize(14));

        Table table = new Table(7);
        table.addHeaderCell("Event");
        table.addHeaderCell("Comm");
        table.addHeaderCell("Lead");
        table.addHeaderCell("Creat");
        table.addHeaderCell("Team");
        table.addHeaderCell("Total");
        table.addHeaderCell("Grade");

        for (Evaluation eval : evaluations) {
            table.addCell(eval.getParticipation().getEvent().getTitle());
            table.addCell(String.valueOf(eval.getCommunication()));
            table.addCell(String.valueOf(eval.getLeadership()));
            table.addCell(String.valueOf(eval.getCreativity()));
            table.addCell(String.valueOf(eval.getTeamwork()));
            table.addCell(String.valueOf(eval.getTotalScore()));
            table.addCell(eval.getGrade());
        }

        document.add(table);
        document.close();
    }


}