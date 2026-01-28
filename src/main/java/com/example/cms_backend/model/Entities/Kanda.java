package com.example.cms_backend.model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "kanda")
public class Kanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Many kandas belong to one parish
    @Column(name = "parish_id")
    private Long parishId;

    // One kanda has many groups (communities)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "kanda_id")
    private List<Group> groups = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "kanda_id")
    List<Notification>  notifications = new ArrayList<>();

    public Kanda() {
    }

    public Kanda(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Kanda(String name) {
        this.name = name;
    }


}
