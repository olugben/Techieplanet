package com.techieplanet.reportingservice.service;

import com.techieplanet.reportingservice.dto.StudentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportCalculatorTest {

    private ReportCalculator reportCalculator;

    @BeforeEach
    void setUp() {
        reportCalculator = new ReportCalculator();
    }

    @Test
    void testGenerateReportForStudent() {
        // Arrange
        StudentResponse student = new StudentResponse();
        student.setName("John Doe");
        student.setMath(80.0);
        student.setEnglish(70.0);
        student.setPhysics(90.0);
        student.setChemistry(60.0);
        student.setBiology(70.0);

        // Act
        Map<String, Object> report = reportCalculator.generateReportForStudent(student);

        // Assert
        assertEquals("John Doe", report.get("name"));

        List<Double> scores = (List<Double>) report.get("scores");
        assertEquals(List.of(80.0, 70.0, 90.0, 60.0, 70.0), scores);

        // Mean = (80 + 70 + 90 + 60 + 70) / 5 = 74
        assertEquals(74.0, (Double) report.get("mean"), 0.001);

        // Sorted scores: [60,70,70,80,90] => median = 70
        assertEquals(70.0, (Double) report.get("median"), 0.001);

        // Mode = 70 (appears twice)
        assertEquals(70.0, (Double) report.get("mode"), 0.001);
    }

    @Test
    void testGenerateReportForStudentWithSingleScore() {
        // Arrange
        StudentResponse student = new StudentResponse();
        student.setName("Single Score");
        student.setMath(100.0);
        student.setEnglish(100.0);
        student.setPhysics(100.0);
        student.setChemistry(100.0);
        student.setBiology(100.0);

        // Act
        Map<String, Object> report = reportCalculator.generateReportForStudent(student);

        // Assert
        assertEquals(100.0, (Double) report.get("mean"), 0.001);
        assertEquals(100.0, (Double) report.get("median"), 0.001);
        assertEquals(100.0, (Double) report.get("mode"), 0.001);
    }

    @Test
    void testGenerateReportForStudentWithEmptyScores() {
        // Arrange
        StudentResponse student = new StudentResponse();
        student.setName("Empty Scores");
        student.setMath(0.0);
        student.setEnglish(0.0);
        student.setPhysics(0.0);
        student.setChemistry(0.0);
        student.setBiology(0.0);

        // Act
        Map<String, Object> report = reportCalculator.generateReportForStudent(student);

        // Assert
        assertEquals(0.0, (Double) report.get("mean"), 0.001);
        assertEquals(0.0, (Double) report.get("median"), 0.001);
        assertEquals(0.0, (Double) report.get("mode"), 0.001);
    }
}
