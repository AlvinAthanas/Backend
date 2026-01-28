package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Entities.Contribution;

public class UpdateContributionCommand {
    private  Long id;
    private Contribution contribution;

    public UpdateContributionCommand( Long id,Contribution contribution) {
        this.contribution = contribution;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Contribution getContribution() {
        return contribution;
    }
}
