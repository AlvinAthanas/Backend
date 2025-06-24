package com.example.cms_backend.Model.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "group_contribution_requirement")
public class GroupContributionRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id")
    private Long groupId;

    private String contributionType;
    private Long targetAmount;
    private LocalDate deadline;
    private String description;

    @Column(name = "declared_by_user_id")
    private Long declaredByUserId;


}