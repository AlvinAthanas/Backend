package com.example.cms_backend.Model.Entities;

import com.example.cms_backend.Model.Enums.AdminVerificationStatus;
import com.example.cms_backend.Model.Enums.Gender;
import com.example.cms_backend.Model.Enums.MaritialStatus;
import jakarta.persistence.Lob;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private MaritialStatus maritalStatus;
    @Column(name = "parish_id")
    private Long parishId;

    @Enumerated(EnumType.STRING)
    @Column(name = "admin_verification_status")
    private AdminVerificationStatus adminVerificationStatus = AdminVerificationStatus.NOT_VERIFIED;


    @Lob
    @Column(name = "profile_picture", columnDefinition = "LONGBLOB")
    private byte[] profilePicture;

    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group>  groups = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "member_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_favorite_parish",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "parish_id")
    )
    private Set<Parish> favoriteParishes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<Feedback> feedbacks;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<Attendance>  attendances;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<Event> events;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<Contribution>  contributions;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<Notification>   notifications;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<FinancialTransaction> transactionsRecorded;

    public User() {

    }

    public User(String name,
                String email,
                String password,
                String phone,
                String address,
                LocalDate birthDate,
                MaritialStatus maritalStatus,
                Gender gender,
                Long parishId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.birthDate = birthDate;
        this.maritalStatus = maritalStatus;
        this.gender = gender;
        this.parishId = parishId;

    }

}
