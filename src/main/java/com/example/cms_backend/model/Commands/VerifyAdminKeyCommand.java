package com.example.cms_backend.model.Commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyAdminKeyCommand {
    private String email;
    private String key;
}
