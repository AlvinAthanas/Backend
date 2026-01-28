package com.example.cms_backend.services.TransactionServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.model.Entities.FinancialTransaction;
import com.example.cms_backend.repositories.FinancialTransactionRepository;
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
