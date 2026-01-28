package com.example.cms_backend.model.Entities;

import com.example.cms_backend.model.Enums.FeedbackType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FeedbackType type;
    private String message;
    private String email;
    private LocalDate date;

    @Column(name = "parish_id")
    private Long parishId;

    @Column(name = "receiver_id")
    private Long receiverId;

    public Feedback(FeedbackType type,
                    String message,
                    String email,
                    LocalDate date,
                    Long receiverId) {
        this.type = type;
        this.message = message;
        this.email = email;
        this.date = date;
        this.receiverId = receiverId;
    }

    public Feedback() {
    }



}
