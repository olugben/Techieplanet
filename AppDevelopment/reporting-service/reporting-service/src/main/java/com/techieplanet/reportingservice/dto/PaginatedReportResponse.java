package com.techieplanet.reportingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class PaginatedReportResponse {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private List<Map<String, Object>> data;
}

