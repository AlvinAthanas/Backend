package com.example.cms_backend.Model.Commands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetExpenseTotalQuery {
    private HttpServletRequest request;
}
