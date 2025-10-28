package com.techieplanet.studentservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double math;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double english;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double physics;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double chemistry;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double biology;
}
