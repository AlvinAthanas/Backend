package com.example.cms_backend.Services.TransactionServices;


import com.example.cms_backend.Repositories.FinancialTransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateTransactionService {
    private final FinancialTransactionRepository transactionRepository;

    public CreateTransactionService(FinancialTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
