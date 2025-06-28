package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.Entities.FinancialTransaction;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionCommand {
    private FinancialTransaction transaction;
    private HttpServletRequest request;

}
