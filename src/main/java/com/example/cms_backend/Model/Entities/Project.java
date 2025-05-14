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
    private Double collected;

    @Lob
    @Column(name = "featured_image", columnDefinition = "LONGBLOB")
    private byte[] featuredImage;


    @Column(name = "parish_id")
    Long parishId;

    public Project() {
    }

    public Project(String name, String description, Double budget, byte[] featuredImage, Long parishId, Double collected) {
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.featuredImage = featuredImage;
        this.parishId = parishId;
        this.collected = collected;
    }
}
