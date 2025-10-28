package com.techieplanet.reportingservice.service;

import com.techieplanet.reportingservice.dto.PaginatedReportResponse;
import com.techieplanet.reportingservice.dto.StudentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    private StudentDataFetcher studentDataFetcher;
    private ReportCalculator reportCalculator;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        studentDataFetcher = mock(StudentDataFetcher.class);
        reportCalculator = mock(ReportCalculator.class);
        reportService = new ReportService(studentDataFetcher, reportCalculator);
    }

    @Test
    void testGenerateReports_noFilters_returnsAllReports() {
        // Arrange
        StudentResponse s1 = new StudentResponse();
        s1.setName("Alice");
        StudentResponse s2 = new StudentResponse();
        s2.setName("Bob");

        when(studentDataFetcher.getAllStudents()).thenReturn(List.of(s1, s2));

        Map<String, Object> report1 = Map.of("name", "Alice", "mean", 80.0);
        Map<String, Object> report2 = Map.of("name", "Bob", "mean", 70.0);

        when(reportCalculator.generateReportForStudent(s1)).thenReturn(report1);
        when(reportCalculator.generateReportForStudent(s2)).thenReturn(report2);

        // Act
        PaginatedReportResponse response = reportService.generateReports(0, 10, null, null, null);

        // Assert
        assertEquals(2, response.getTotalElements());
        assertEquals(1, response.getTotalPages()); // 10 per page, only 2 items
        assertEquals(2, response.getData().size());
        assertTrue(response.getData().contains(report1));
        assertTrue(response.getData().contains(report2));

        verify(studentDataFetcher, times(1)).getAllStudents();
        verify(reportCalculator, times(1)).generateReportForStudent(s1);
        verify(reportCalculator, times(1)).generateReportForStudent(s2);
    }

    @Test
    void testGenerateReports_withNameFilter() {
        // Arrange
        StudentResponse s1 = new StudentResponse();
        s1.setName("Alice");
        StudentResponse s2 = new StudentResponse();
        s2.setName("Bob");

        when(studentDataFetcher.getAllStudents()).thenReturn(List.of(s1, s2));

        Map<String, Object> report1 = Map.of("name", "Alice", "mean", 80.0);
        Map<String, Object> report2 = Map.of("name", "Bob", "mean", 70.0);

        when(reportCalculator.generateReportForStudent(s1)).thenReturn(report1);
        when(reportCalculator.generateReportForStudent(s2)).thenReturn(report2);

        // Act
        PaginatedReportResponse response = reportService.generateReports(0, 10, "bob", null, null);

        // Assert
        assertEquals(1, response.getTotalElements());
        assertEquals("Bob", response.getData().get(0).get("name"));
    }

    @Test
    void testGenerateReports_withMeanFilter() {
        // Arrange
        StudentResponse s1 = new StudentResponse();
        s1.setName("Alice");
        StudentResponse s2 = new StudentResponse();
        s2.setName("Bob");

        when(studentDataFetcher.getAllStudents()).thenReturn(List.of(s1, s2));

        Map<String, Object> report1 = Map.of("name", "Alice", "mean", 80.0);
        Map<String, Object> report2 = Map.of("name", "Bob", "mean", 70.0);

        when(reportCalculator.generateReportForStudent(s1)).thenReturn(report1);
        when(reportCalculator.generateReportForStudent(s2)).thenReturn(report2);

        // Act
        PaginatedReportResponse response = reportService.generateReports(0, 10, null, 75.0, null);

        // Assert
        assertEquals(1, response.getTotalElements());
        assertEquals("Alice", response.getData().get(0).get("name"));
    }

    @Test
    void testGenerateReports_emptyStudentList() {
        // Arrange
        when(studentDataFetcher.getAllStudents()).thenReturn(List.of());

        // Act
        PaginatedReportResponse response = reportService.generateReports(0, 10, null, null, null);

        // Assert
        assertEquals(0, response.getTotalElements());
        assertEquals(0, response.getTotalPages());
        assertTrue(response.getData().isEmpty());
    }

    @Test
    void testGenerateReports_pagination() {
        // Arrange
        StudentResponse s1 = new StudentResponse(); s1.setName("A");
        StudentResponse s2 = new StudentResponse(); s2.setName("B");
        StudentResponse s3 = new StudentResponse(); s3.setName("C");

        when(studentDataFetcher.getAllStudents()).thenReturn(List.of(s1, s2, s3));

        when(reportCalculator.generateReportForStudent(any())).thenAnswer(invocation -> {
            StudentResponse s = invocation.getArgument(0);
            return Map.of("name", s.getName(), "mean", 80.0);
        });

        // Act: page size 2, page 0
        PaginatedReportResponse responsePage0 = reportService.generateReports(0, 2, null, null, null);
        PaginatedReportResponse responsePage1 = reportService.generateReports(1, 2, null, null, null);

        // Assert
        assertEquals(3, responsePage0.getTotalElements());
        assertEquals(2, responsePage0.getData().size());
        assertEquals(2, responsePage0.getTotalPages());

        assertEquals(1, responsePage1.getData().size());
        assertEquals("C", responsePage1.getData().get(0).get("name"));
    }
}
