package com.example.cms_backend.Model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "parish")
public class Parish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "parish_priest")
    private String parishPriest;
    private String location;
    private String contactInfo;
    @Column(name = "featured_image")
    private String imageUrl;

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

    public Parish(String name, String parishPriest, String location, String contactInfo,String imageUrl, Long dioceseId) {
        this.name = name;
        this.parishPriest = parishPriest;
        this.location = location;
        this.contactInfo = contactInfo;
        this.imageUrl = imageUrl;
        this.dioceseId = dioceseId;
    }

    public Parish() {
    }

}

