package com.example.cms_backend.Model.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    private Double budget;

    @Lob
    @Column(name = "featured_image")
    private byte[] featuredImage;

    @Column(name = "parish_id")
    Long parentId;

    public Project() {
    }

    public Project(String name, String description, Double budget, byte[] featuredImage, Long parentId) {
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.featuredImage = featuredImage;
        this.parentId = parentId;
    }
}
