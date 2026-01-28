package com.example.cms_backend.services.TransactionServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.TransactionNotFoundException;
import com.example.cms_backend.model.DTO.TransactionDTO;
import com.example.cms_backend.model.Entities.FinancialTransaction;
import com.example.cms_backend.model.Commands.UpdateTransactionCommand;
import com.example.cms_backend.repositories.FinancialTransactionRepository;
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

            FinancialTransaction transaction = transactionOptional.get();
            transaction.setId(command.getId());
            transaction.setType(command.getTransaction().getType());
            transaction.setDescription(command.getTransaction().getDescription());
            transaction.setAmount(command.getTransaction().getAmount());
            transaction.setDate(command.getTransaction().getDate());
            transaction.setCategory(command.getTransaction().getCategory());
            transaction.setPaymentMethod(command.getTransaction().getPaymentMethod());
            transactionRepository.save(transaction);
            return ResponseEntity.ok(new TransactionDTO(transaction));
        }
        throw new TransactionNotFoundException();
    }
}
