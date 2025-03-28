package com.example.cms_backend.Model.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
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

    @Column(name = "user_id")
    private Long userId;

    public Contribution(Long amount, String type, String description, LocalDate date) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.date = date;

    }

    public Contribution() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getParishId() {
        return parishId;
    }

    public void setParishId(Long parishId) {
        this.parishId = parishId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
