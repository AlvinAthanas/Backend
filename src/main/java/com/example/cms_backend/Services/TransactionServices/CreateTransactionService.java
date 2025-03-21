package com.example.cms_backend.Services.TransactionServices;


import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.DTO.TransactionDTO;
import com.example.cms_backend.Model.Entities.FinancialTransaction;
import com.example.cms_backend.Repositories.FinancialTransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateTransactionService implements Command<FinancialTransaction, TransactionDTO> {
    private final FinancialTransactionRepository transactionRepository;

    public CreateTransactionService(FinancialTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ResponseEntity<TransactionDTO> execute(FinancialTransaction transaction) {
        transactionRepository.save(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionDTO(transaction));
    }
}
