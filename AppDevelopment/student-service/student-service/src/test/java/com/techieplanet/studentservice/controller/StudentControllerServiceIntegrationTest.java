package com.techieplanet.studentservice.controller;

import com.techieplanet.studentservice.dto.StudentRequest;
import com.techieplanet.studentservice.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerServiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/students";
    }

    @Test
    void testCreateAndGetStudent() {
        // Create student
        StudentRequest request = new StudentRequest();
        request.setName("John Doe");
        request.setMath(80.0);
        request.setEnglish(75.0);
        request.setPhysics(85.0);
        request.setChemistry(70.0);
        request.setBiology(90.0);

        ResponseEntity<Student> postResponse = restTemplate.postForEntity(baseUrl, request, Student.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student created = postResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo("John Doe");

        // 2️⃣ Get all students
        ResponseEntity<Student[]> getAllResponse = restTemplate.getForEntity(baseUrl, Student[].class);
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Student> students = List.of(getAllResponse.getBody());
        assertThat(students).isNotEmpty();
        assertThat(students).extracting(Student::getName).contains("John Doe");

        // 3️⃣ Get by ID
        ResponseEntity<Student> getByIdResponse = restTemplate.getForEntity(baseUrl + "/" + created.getId(), Student.class);
        assertThat(getByIdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getByIdResponse.getBody().getName()).isEqualTo("John Doe");
    }

    @Test
    void testDeleteStudent() {
        // Create a student first
        StudentRequest request = new StudentRequest();
        request.setName("Jane Doe");
        request.setMath(65.0);
        request.setEnglish(70.0);
        request.setPhysics(75.0);
        request.setChemistry(80.0);
        request.setBiology(85.0);

        Student created = restTemplate.postForEntity(baseUrl, request, Student.class).getBody();
        assertThat(created).isNotNull();

        // Delete
        restTemplate.delete(baseUrl + "/" + created.getId());

        // Verify deletion
        ResponseEntity<Student> getByIdResponse = restTemplate.getForEntity(baseUrl + "/" + created.getId(), Student.class);
        assertThat(getByIdResponse.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR); // because your service throws RuntimeException
    }
}
