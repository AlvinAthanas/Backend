package com.example.cms_backend.services.TransactionServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.TransactionNotFoundException;
import com.example.cms_backend.model.Entities.FinancialTransaction;
import com.example.cms_backend.repositories.FinancialTransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetTransactionService implements Query<Long, FinancialTransaction> {

    private final FinancialTransactionRepository transactionRepository;

    public GetTransactionService(FinancialTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ResponseEntity<FinancialTransaction> execute(Long input) {
        Optional<FinancialTransaction> transactionOptional = transactionRepository.findById(input);
        if (transactionOptional.isPresent()) {
            return ResponseEntity.ok(transactionOptional.get());
        }
        throw new TransactionNotFoundException();
    }
}
