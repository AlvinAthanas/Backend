package com.example.cms_backend.Services.TransactionServices;

import com.example.cms_backend.Repositories.FinancialTransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class GetTransactionRepository {

    private final FinancialTransactionRepository transactionRepository;

    public GetTransactionRepository(FinancialTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
