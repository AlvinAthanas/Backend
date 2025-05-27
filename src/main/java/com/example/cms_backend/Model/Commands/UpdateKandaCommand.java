package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.Entities.Kanda;

public class UpdateKandaCommand {
    private Long id;
    private Kanda kanda;

    public UpdateKandaCommand(Long id, Kanda kanda) {
        this.id = id;
        this.kanda = kanda;
    }

    public Long getId() {
        return id;
    }

    public Kanda getKanda() {
        return kanda;
    }
}

