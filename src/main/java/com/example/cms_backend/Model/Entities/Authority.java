package com.example.cms_backend.Model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "authority")
@Data
public class Authority {
    @Id
    private int id;
    private String name;

    @ManyToMany(mappedBy = "authorities")
    @JsonIgnore
    private Set<User> users;
}
