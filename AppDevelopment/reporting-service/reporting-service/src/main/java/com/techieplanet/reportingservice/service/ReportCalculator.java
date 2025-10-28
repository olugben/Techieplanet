package com.techieplanet.reportingservice.service;

import com.techieplanet.reportingservice.dto.StudentResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportCalculator {

    /**
     * Generates a report for a single student with mean, median, and mode.
     */
    public Map<String, Object> generateReportForStudent(StudentResponse s) {
        List<Double> scores = List.of(
                s.getMath(),
                s.getEnglish(),
                s.getPhysics(),
                s.getChemistry(),
                s.getBiology()
        );

        Map<String, Object> report = new LinkedHashMap<>();
        report.put("name", s.getName());
        report.put("scores", scores);
        report.put("mean", calculateMean(scores));
        report.put("median", calculateMedian(scores));
        report.put("mode", calculateMode(scores));

        return report;
    }

    /** Calculates mean of the given scores. */
    private double calculateMean(List<Double> scores) {
        return scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    /** Calculates median of the given scores. */
    private double calculateMedian(List<Double> scores) {
        List<Double> sorted = new ArrayList<>(scores);
        Collections.sort(sorted);
        int middle = sorted.size() / 2;
        if (sorted.size() % 2 == 0) {
            return (sorted.get(middle - 1) + sorted.get(middle)) / 2.0;
        }
        return sorted.get(middle);
    }

    /** Calculates mode of the given scores. */
    private double calculateMode(List<Double> scores) {
        Map<Double, Long> freq = new HashMap<>();
        for (Double score : scores) {
            freq.put(score, freq.getOrDefault(score, 0L) + 1);
        }
        return freq.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0.0);
    }
}
