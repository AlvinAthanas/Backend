package com.example.cms_backend.Model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "group_tb")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @Column(name = "kanda_id")
    private Long kandaId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    List<Notification>  notifications;

    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    private Set<User> users;

    @Column(name = "parish_id")
    private Long parishId;

    public Group(String name, String description, Set<User> users) {
        this.name = name;
        this.description = description;
        this.users = users;
    }



    public Group() {
    }


}
