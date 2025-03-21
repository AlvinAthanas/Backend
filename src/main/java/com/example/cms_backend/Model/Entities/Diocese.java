package com.example.cms_backend.Model.Entities;

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

    public Diocese(String name, String location, String bishopName) {
        this.name = name;
        this.location = location;
        this.bishopName = bishopName;
    }
    public Diocese() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBishopName() {
        return bishopName;
    }

    public void setBishopName(String bishopName) {
        this.bishopName = bishopName;
    }

    public List<Parish> getParishes() {
        return parishes;
    }

    public void setParishes(List<Parish> parishes) {
        this.parishes = parishes;
    }
}

