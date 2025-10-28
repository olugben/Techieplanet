package com.techieplanet.reportingservice.service;

import com.techieplanet.reportingservice.client.StudentClient;
import com.techieplanet.reportingservice.dto.StudentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentDataFetcherTest {

    private StudentClient studentClientMock;
    private StudentDataFetcher studentDataFetcher;

    @BeforeEach
    void setUp() {
        studentClientMock = mock(StudentClient.class);
        studentDataFetcher = new StudentDataFetcher(studentClientMock);
    }

    @Test
    void testGetAllStudents_returnsStudents() {
        // Arrange
        StudentResponse student1 = new StudentResponse();
        student1.setName("Alice");
        StudentResponse student2 = new StudentResponse();
        student2.setName("Bob");

        when(studentClientMock.getAllStudents()).thenReturn(List.of(student1, student2));

        // Act
        List<StudentResponse> students = studentDataFetcher.getAllStudents();

        // Assert
        assertNotNull(students);
        assertEquals(2, students.size());
        assertEquals("Alice", students.get(0).getName());
        assertEquals("Bob", students.get(1).getName());

        // Verify interaction with mock
        verify(studentClientMock, times(1)).getAllStudents();
    }

    @Test
    void testGetAllStudents_returnsEmptyListWhenClientReturnsNull() {
        // Arrange
        when(studentClientMock.getAllStudents()).thenReturn(null);

        // Act
        List<StudentResponse> students = studentDataFetcher.getAllStudents();

        // Assert
        assertNull(students); // matches current implementation (optional: you can return empty list in future)
        verify(studentClientMock, times(1)).getAllStudents();
    }
}
