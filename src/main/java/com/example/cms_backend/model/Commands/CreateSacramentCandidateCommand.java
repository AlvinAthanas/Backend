package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSacramentCandidateCommand {
    private String fullName;
    private Gender gender;
    private String phoneNumber;
    private String guardianName;
    private Long userId; // optional if already exists in user table
}
