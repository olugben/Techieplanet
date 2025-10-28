
package com.techieplanet.studentservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double math;

    @Column(nullable = false)
    private Double english;

    @Column(nullable = false)
    private Double physics;

    @Column(nullable = false)
    private Double chemistry;

    @Column(nullable = false)
    private Double biology;
}
