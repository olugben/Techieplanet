package com.techieplanet.reportingservice.controller;

import com.techieplanet.reportingservice.dto.PaginatedReportResponse;
import com.techieplanet.reportingservice.service.ReportService;
import jakarta.validation.constraints.Min;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReportController.class)
@Import(ReportControllerTest.TestConfig.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportService reportService;

    private PaginatedReportResponse sampleResponse;

    @BeforeEach
    void setUp() {
        sampleResponse = new PaginatedReportResponse(
                0,
                10,
                2,
                1,
                List.of(
                        Map.of("name", "Alice", "mean", 80.0),
                        Map.of("name", "Bob", "mean", 70.0)
                )
        );

        Mockito.reset(reportService);

        Mockito.when(reportService.generateReports(anyInt(), anyInt(), nullable(String.class),
                        nullable(Double.class), nullable(Double.class)))
                .thenReturn(sampleResponse);
    }

    @Test
    void testGenerateReports_defaultParams() throws Exception {
        mockMvc.perform(get("/api/reports")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.data[0].name").value("Alice"))
                .andExpect(jsonPath("$.data[1].name").value("Bob"));
    }

    @Test
    void testGenerateReports_withQueryParams() throws Exception {
        mockMvc.perform(get("/api/reports")
                        .param("page", "1")
                        .param("size", "5")
                        .param("name", "alice")
                        .param("minMean", "75.0")
                        .param("maxMean", "85.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.data[0].name").value("Alice"));
    }

    @Test
    void testGenerateReports_validationFailure() throws Exception {
        // Negative page/zero size should return 400 due to @Min validation
        mockMvc.perform(get("/api/reports")
                        .param("page", "-1")
                        .param("size", "0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    static class TestConfig {
        @Bean
        public ReportService reportService() {
            return Mockito.mock(ReportService.class);
        }
    }
}
