package com.example.cms_backend.model.Commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchParishCommand {
    private String name;
    private boolean includeCommunities;

    // getters and setters
}

