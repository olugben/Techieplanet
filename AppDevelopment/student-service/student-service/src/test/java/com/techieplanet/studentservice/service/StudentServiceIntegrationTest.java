package com.techieplanet.studentservice.service;

import com.techieplanet.studentservice.dto.StudentRequest;
import com.techieplanet.studentservice.entity.Student;
import com.techieplanet.studentservice.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class StudentServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("studentdb_test")
                    .withUsername("postgres")
                    .withPassword("password");

    @DynamicPropertySource
    static void configureTestDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void cleanDatabase() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldCreateAndFetchStudentSuccessfully() {
        // given
        StudentRequest request = StudentRequest.builder()
                .name("Jane Doe")
                .math(85.0)
                .english(78.5)
                .physics(90.0)
                .chemistry(88.0)
                .biology(92.0)
                .build();

        // when
        Student saved = studentService.createStudent(request);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Jane Doe");

        // and when
        List<Student> all = studentService.getAllStudents();

        assertThat(all).hasSize(1);
        assertThat(all.get(0).getMath()).isEqualTo(85.0);
    }
}
