package com.techieplanet.studentservice.service;

import com.techieplanet.studentservice.dto.StudentRequest;
import com.techieplanet.studentservice.entity.Student;
import com.techieplanet.studentservice.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    /**
     * Create a new student.
     */
    public Student createStudent(StudentRequest request) {
        Student student = Student.builder()
                .name(request.getName())
                .math(request.getMath())
                .english(request.getEnglish())
                .physics(request.getPhysics())
                .chemistry(request.getChemistry())
                .biology(request.getBiology())
                .build();
        return studentRepository.save(student);
    }

    /**
     * Retrieve all students.
     */
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Retrieve a student by ID. Throws a custom exception if not found.
     */
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    /**
     * Delete a student by ID. Throws exception if student doesn't exist.
     */
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    // Custom Exception
    public static class StudentNotFoundException extends RuntimeException {
        public StudentNotFoundException(Long id) {
            super("Student not found with id: " + id);
        }
    }
}
