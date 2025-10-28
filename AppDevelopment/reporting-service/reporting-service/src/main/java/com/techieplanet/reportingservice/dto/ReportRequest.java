package com.techieplanet.reportingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ReportRequest {

    @Schema(description = "Page index (starting from 0)", example = "0")
    @Min(value = 0)
    private int page = 0;

    @Schema(description = "Number of items per page", example = "10")
    @Min(value = 1)
    private int size = 10;

    @Schema(description = "Optional student name filter", nullable = true, example = "")
    private String name;

    @Schema(description = "Optional minimum mean filter", nullable = true, example = "")
    private Double minMean;

    @Schema(description = "Optional maximum mean filter", nullable = true, example = "")
    private Double maxMean;
}
