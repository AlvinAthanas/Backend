package com.example.cms_backend.model.Commands;

import lombok.Getter;

@Getter
public class GetUserCommand {
    private Long id;
    private String email;

    public GetUserCommand(Long id, String email) {
        this.id = id;
        this.email = email;
    }


}

