package com.example.cms_backend.Model.Commands;

import java.util.Set;

public class UpdateUserAuthoritiesCommand {
    private Long id;
    private Set<String> authorityNames;

    public UpdateUserAuthoritiesCommand(Long id, Set<String> authorityNames) {
        this.id = id;
        this.authorityNames = authorityNames;
    }

    public Long getId() {
        return id;
    }

    public Set<String> getAuthorityNames() {
        return authorityNames;
    }
}