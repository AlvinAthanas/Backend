package com.example.cms_backend.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "diocese")
public class Diocese {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String bishopName;

    @OneToMany(mappedBy = "diocese")
    private List<Parish> parishes;

    // Getters and Setters
}

