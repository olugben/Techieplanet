package com.techieplanet.reportingservice.service;

import com.techieplanet.reportingservice.dto.PaginatedReportResponse;
import com.techieplanet.reportingservice.dto.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final StudentDataFetcher studentDataFetcher;
    private final ReportCalculator reportCalculator;

    /**
     * Generates paginated and filtered reports for all students.
     *
     * @param page Page index (0-based)
     * @param size Number of items per page
     * @param name Filter by student name (optional)
     * @param minMean Filter by minimum mean (optional)
     * @param maxMean Filter by maximum mean (optional)
     * @return PaginatedReportResponse containing filtered reports
     */
    public PaginatedReportResponse generateReports(int page, int size, String name, Double minMean, Double maxMean) {

        // Fetch students safely using Optional
        List<StudentResponse> students = Optional.ofNullable(studentDataFetcher.getAllStudents())
                .orElse(Collections.emptyList());

        if (students.isEmpty()) {
            return new PaginatedReportResponse(page, size, 0, 0, Collections.emptyList());
        }

        // Generate reports
        List<Map<String, Object>> reports = students.stream()
                .map(reportCalculator::generateReportForStudent)
                .collect(Collectors.toList());

        // Filter reports using helper methods
        List<Map<String, Object>> filteredReports = reports.stream()
                .filter(r -> matchesName(r, name))
                .filter(r -> matchesMean(r, minMean, maxMean))
                .collect(Collectors.toList());

        // Pagination
        int total = filteredReports.size();
        int fromIndex = Math.min(page * size, total);
        int toIndex = Math.min(fromIndex + size, total);
        List<Map<String, Object>> pagedReports = filteredReports.subList(fromIndex, toIndex);
        int totalPages = (int) Math.ceil((double) total / size);


        return new PaginatedReportResponse(page, size, total, totalPages,
                Collections.unmodifiableList(pagedReports));
    }

    /** Checks if the report matches the given name filter. */
    private boolean matchesName(Map<String, Object> report, String name) {
        return name == null || ((String) report.get("name")).toLowerCase().contains(name.toLowerCase());
    }

    /** Checks if the report matches the given mean filter range. */
    private boolean matchesMean(Map<String, Object> report, Double minMean, Double maxMean) {
        Double mean = (Double) report.get("mean");
        return (minMean == null || mean >= minMean) && (maxMean == null || mean <= maxMean);
    }
}
