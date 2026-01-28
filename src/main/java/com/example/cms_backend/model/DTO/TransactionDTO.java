package com.example.cms_backend.model.DTO;

import com.example.cms_backend.model.Entities.FinancialTransaction;

import java.time.LocalDate;

public class TransactionDTO {
    private Long id;
    private LocalDate date;

    public TransactionDTO(FinancialTransaction transaction) {
        this.id = transaction.getId();
        this.date = transaction.getDate();
    }

    public Long getId() {
        return id;
    }
    public LocalDate getDate() {
        return date;
    }
}
