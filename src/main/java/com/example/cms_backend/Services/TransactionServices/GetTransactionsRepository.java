package com.example.cms_backend.Services.TransactionServices;

import com.example.cms_backend.Repositories.FinancialTransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class GetTransactionsRepository {
    private final FinancialTransactionRepository transactionRepository;

    public GetTransactionsRepository(FinancialTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
