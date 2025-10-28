package com.techieplanet.reportingservice.controller;

import com.techieplanet.reportingservice.dto.PaginatedReportResponse;
import com.techieplanet.reportingservice.dto.ReportRequest;
import com.techieplanet.reportingservice.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /**
     * Generate paginated student reports with mean, median, and mode.
     * Supports filtering by student name and mean score range.
     *
     * @param request The filtering and pagination parameters
     * @return PaginatedReportResponse containing filtered reports
     */
    @GetMapping
    @Operation(summary = "Generate student reports with mean, median, and mode (supports pagination & filtering)")
    public ResponseEntity<PaginatedReportResponse> generateReports(@ParameterObject @Valid ReportRequest request) {
        PaginatedReportResponse response = reportService.generateReports(
                request.getPage(),
                request.getSize(),
                request.getName(),
                request.getMinMean(),
                request.getMaxMean()
        );

        // Include total count in headers for clients to read
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(response.getTotalElements()))
                .body(response);
    }
}
