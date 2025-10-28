package com.techieplanet.reportingservice.service;

import com.techieplanet.reportingservice.client.StudentClient;
import com.techieplanet.reportingservice.dto.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentDataFetcher {

    private final StudentClient studentClient;

    /**
     * Fetches all students from the client.
     */
    public List<StudentResponse> getAllStudents() {
        return studentClient.getAllStudents();
    }
}
