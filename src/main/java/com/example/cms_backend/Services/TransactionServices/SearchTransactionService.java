package com.example.cms_backend.Services.TransactionServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.FinancialTransaction;
import com.example.cms_backend.Repositories.FinancialTransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchTransactionService implements Query<String, List<FinancialTransaction>> {

    private final FinancialTransactionRepository transactionRepository;

    public SearchTransactionService(FinancialTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ResponseEntity<List<FinancialTransaction>> execute(String type) {
        return ResponseEntity.ok(transactionRepository.findFinancialTransactionsByTypeContaining(type));
    }
}
