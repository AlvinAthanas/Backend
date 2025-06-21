package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.Enums.Gender;
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
