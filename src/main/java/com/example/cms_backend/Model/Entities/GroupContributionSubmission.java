package com.example.cms_backend.Model.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "group_contribution_submission")
public class GroupContributionSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requirement_id")
    private Long requirementId;

    @Column(name = "user_id")
    private Long userId;

    private Long amount;

    private LocalDate date;

    private String note; // optional
}
