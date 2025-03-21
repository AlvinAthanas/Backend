package com.example.cms_backend.Services.TransactionServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.TransactionNotFoundException;
import com.example.cms_backend.Model.DTO.TransactionDTO;
import com.example.cms_backend.Model.Entities.FinancialTransaction;
import com.example.cms_backend.Model.UpdateCommands.UpdateTransactionCommand;
import com.example.cms_backend.Repositories.FinancialTransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateTransactionService implements Command<UpdateTransactionCommand, TransactionDTO> {
    private final FinancialTransactionRepository transactionRepository;

    public UpdateTransactionService(FinancialTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public ResponseEntity<TransactionDTO> execute(UpdateTransactionCommand command) {
        Optional<FinancialTransaction> transactionOptional = transactionRepository.findById(command.getId());
        if (transactionOptional.isPresent()) {
            FinancialTransaction transaction = command.getTransaction();
            transaction.setId(command.getId());
            transactionRepository.save(transaction);
            return ResponseEntity.ok(new TransactionDTO(transaction));
        }
        throw new TransactionNotFoundException();
    }
}
