package com.example.cms_backend.Model.Commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyAdminKeyCommand {
    private String email;
    private String key;
}
