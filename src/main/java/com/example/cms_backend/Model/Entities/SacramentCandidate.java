package com.example.cms_backend.Model.Entities;

import com.example.cms_backend.Model.Enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sacrament_candidates")
public class SacramentCandidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Optional link to a registered user (nullable)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "guardian_name")
    private String guardianName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private String contactInfo;

    @Column(name = "has_account", nullable = false)
    private boolean hasAccount = false;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt = LocalDate.now();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    List<SacramentRegistration> sacramentRegistrations;

    public SacramentCandidate() {}
}
