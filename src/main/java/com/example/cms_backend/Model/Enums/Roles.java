package com.example.cms_backend.Model.Enums;

import lombok.Getter;

@Getter
public enum Roles {
    PARISHIONER("PARISHIONER"),
    COMMITTEE_CHAIRPERSON("COMMITTEE_CHAIRPERSON"),
    COMMITTEE_SECRETARY("COMMITTEE_SECRETARY"),
    COMMITTEE_TREASURER("COMMITTEE_TREASURER"),
    CATECHIST("CATECHIST"),
    COMMUNITY_CHAIRPERSON("COMMUNITY_CHAIRPERSON"),
    COMMUNITY_SECRETARY("COMMUNITY_SECRETARY"),
    COMMUNITY_TREASURER("COMMUNITY_TREASURER"),
    PARISH_MEMBER("PARISH_MEMBER");

    // Getter for the role name
    private final String roleName;

    // Constructor to set the roleName
    Roles(String roleName) {
        this.roleName = roleName;
    }

    // Optional: Override toString() to return the custom role name
    @Override
    public String toString() {
        return roleName;
    }
}
