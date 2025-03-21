package com.example.cms_backend.Model.UpdateCommands;

import com.example.cms_backend.Model.Entities.FinancialTransaction;

public class UpdateTransactionCommand {
    private Long id;
    private FinancialTransaction transaction;

    public UpdateTransactionCommand(Long id, FinancialTransaction transaction) {
        this.id = id;
        this.transaction = transaction;
    }

    public Long getId() {
        return id;
    }

    public FinancialTransaction getTransaction() {
        return transaction;
    }
}
