package com.example.cms_backend.services.TransactionServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.TransactionNotFoundException;
import com.example.cms_backend.model.Entities.FinancialTransaction;
import com.example.cms_backend.repositories.FinancialTransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteTransactionService implements Command<Long, Void> {
    private final FinancialTransactionRepository transactionRepository;

    public DeleteTransactionService(FinancialTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Long input) {
        Optional<FinancialTransaction> transaction = transactionRepository.findById(input);
        if (transaction.isPresent()) {
            transactionRepository.delete(transaction.get());
        }
        throw new TransactionNotFoundException();
    }
}
