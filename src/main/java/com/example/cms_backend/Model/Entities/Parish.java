package com.example.cms_backend.Model.Entities;

import jakarta.persistence.*;

import java.util.List;

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

    public Parish(Long id, String name, String location, String contactInfo, Diocese diocese) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;

    }

    public Parish() {
    }
    // Getters and Setters

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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    public List<FinancialTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<FinancialTransaction> transactions) {
        this.transactions = transactions;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Long getDioceseId() {
        return dioceseId;
    }

    public void setDioceseId(Long dioceseId) {
        this.dioceseId = dioceseId;
    }
}

