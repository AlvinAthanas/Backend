package com.example.cms_backend.Model.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "parish")
public class Parish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String contactInfo;

    //PARISHES and DIOCESE
    @Column(name = "diocese_id") //TODO: set nullable to false
    private Long dioceseId;

    //PARISH AND USERS
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parish_id")
    private List<User> users;

    //PARISH AND GROUPS
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parish_id")
    private List<Group> groups;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parish_id")
    private List<Contribution> contributions;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parish_id")
    private List<FinancialTransaction> transactions;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parish_id")
    private List<Event> events;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parish_id")
    private List<Project> projects;

    public Parish(Long id, String name, String location, String contactInfo, Long dioceseId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;
        this.dioceseId = dioceseId;
    }

    public Parish() {
    }

}

