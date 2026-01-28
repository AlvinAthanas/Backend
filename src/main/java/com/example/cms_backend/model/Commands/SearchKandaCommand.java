package com.example.cms_backend.model.Commands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchKandaCommand {
    private String name;
    private HttpServletRequest request;
}
