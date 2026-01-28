package com.example.cms_backend.model.Entities;

import com.example.cms_backend.model.Enums.SacramentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "sacrament_registrations")
public class SacramentRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id", nullable = true)
    private Long candidateId;

    @Enumerated(EnumType.STRING)
    @Column(name = "sacrament_type", nullable = false)
    private SacramentType sacramentType;

    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "registration_date", nullable = true)
    private LocalDate registrationDate = LocalDate.now();

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "parish_id")
    private Long parishId;

    public SacramentRegistration() {}
}


