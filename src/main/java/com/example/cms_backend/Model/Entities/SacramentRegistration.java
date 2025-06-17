package com.example.cms_backend.Model.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sacrament_registrations")
public class SacramentRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the user who made the request
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String sacramentType; // e.g. Baptism, Confirmation, Marriage, etc.

    @Column(name = "preferred_date")
    private LocalDateTime preferredDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED, etc.

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Optional: to support soft delete
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public SacramentRegistration() {
    }

    public SacramentRegistration(Long userId, String sacramentType, LocalDateTime preferredDate, String notes) {
        this.userId = userId;
        this.sacramentType = sacramentType;
        this.preferredDate = preferredDate;
        this.notes = notes;
    }
}

