package com.example.cms_backend.Model.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;
    private LocalDate date;
    private Boolean isGlobal;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "kanda_id")
    private Long kandaId;

    @Column(name = "parish_id")
    private Long parishId;

    public Notification() {
    }

    public Notification(String title, String message, LocalDate date, Boolean isGlobal) {
        this.title = title;
        this.message = message;
        this.date = date;
        this.isGlobal = isGlobal;
    }

}
