package com.example.cms_backend.Services.TransactionServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.FinancialTransaction;
import com.example.cms_backend.Repositories.FinancialTransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CreateTransactionsService implements Command<List<FinancialTransaction>,List<FinancialTransaction>> {

    private final FinancialTransactionRepository transactionRepository;

    public CreateTransactionsService(FinancialTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ResponseEntity<List<FinancialTransaction>> execute(List<FinancialTransaction> transactions) {
        transactionRepository.saveAll(transactions);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactions);
    }
}
