package com.example.cms_backend.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateProjectDTO {
    private String name;
    private String description;
    private Double budget;
    private Double collected;

}
