package com.example.cms_backend.Model;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dioceseId", referencedColumnName = "id") //TODO: set nullable to false
    private Diocese diocese;

    //PARISH AND USERS
    @OneToMany(mappedBy = "parish")
    private List<User> users;

    //PARISH AND GROUPS
    @OneToMany(mappedBy = "parish")
    private List<Group> groups;

    @OneToMany(mappedBy = "parish")
    private List<Contribution> contributions;

    @OneToMany(mappedBy = "parish")
    private List<FinancialTransaction> transactions;

    @OneToMany(mappedBy = "parish")
    private List<Event> events;

    public Parish(Long id, String name, String location, String contactInfo, Diocese diocese) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;
        this.diocese = diocese;
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

    public Diocese getDiocese() {
        return diocese;
    }

    public void setDiocese(Diocese diocese) {
        this.diocese = diocese;
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
}

