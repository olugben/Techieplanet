package com.techieplanet.reportingservice.dto;

import lombok.Data;

@Data
public class StudentResponse {
    private Long id;
    private String name;
    private Double math;
    private Double english;
    private Double physics;
    private Double chemistry;
    private Double biology;
}
