package com.example.cms_backend.model.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "contribution")
public class Contribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;
    private String type;
    private String description;
    private LocalDate date;

    @Column(name = "parish_id")
    private Long parishId;

    @Column(name = "recorder_id")
    private Long recorderId;

    @Column(name = "user_id")
    private Long userId;

    public Contribution(Long amount, String type, String description, LocalDate date) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.date = date;

    }

    public Contribution() {}

}
