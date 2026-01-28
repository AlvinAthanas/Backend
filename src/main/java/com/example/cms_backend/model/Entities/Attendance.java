package com.example.cms_backend.model.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "attendance")
@Data
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "is_present")
    private Boolean isPresent;
    private LocalDate date;


    @Column(name = "user_id")
    private Long userId;

    @Column(name = "event_id")
    private Long eventId;

    public Attendance(Boolean isPresent, LocalDate date) {
        this.isPresent = isPresent;
        this.date = date;
    }

    public Attendance() {}

}
