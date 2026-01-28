package com.example.cms_backend.model.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String location;
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "parish_id")
    private Long parishId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private List<Attendance> attendances;

    public Event(String name, String description, String location, LocalDateTime dateTime) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
    }

    public Event() {
    }

}
