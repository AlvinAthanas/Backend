package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Entities.Diocese;

public class UpdateDioceseCommand {
    private Long id;
    private Diocese diocese;

    public UpdateDioceseCommand(Long id, Diocese diocese) {
        this.id = id;
        this.diocese = diocese;
    }

    public Long getId() {
        return id;
    }

    public Diocese getDiocese() {
        return diocese;
    }
}
