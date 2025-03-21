package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.DTO.TransactionDTO;
import com.example.cms_backend.Model.Entities.FinancialTransaction;
import com.example.cms_backend.Model.UpdateCommands.UpdateTransactionCommand;
import com.example.cms_backend.Services.TransactionServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    private final CreateTransactionService createTransactionService;
    private final GetTransactionService getTransactionService;
    private final UpdateTransactionService updateTransactionService;
    private final DeleteTransactionService deleteTransactionService;
    private final GetTransactionsService  getTransactionsService;

    public TransactionController(CreateTransactionService createTransactionService,
                                 GetTransactionService getTransactionService,
                                 UpdateTransactionService updateTransactionService,
                                 DeleteTransactionService deleteTransactionService,
                                 GetTransactionsService getTransactionsService) {
        this.createTransactionService = createTransactionService;
        this.getTransactionService = getTransactionService;
        this.updateTransactionService = updateTransactionService;
        this.deleteTransactionService = deleteTransactionService;
        this.getTransactionsService = getTransactionsService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody FinancialTransaction transaction){
        return createTransactionService.execute(transaction);
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<FinancialTransaction> getTransaction(@PathVariable Long id){
        return getTransactionService.execute(id);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<FinancialTransaction>> getTransactions(){
        return getTransactionsService.execute(null);
    }

    @PutMapping("/transaction/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @RequestBody FinancialTransaction transaction){
        return updateTransactionService.execute(new UpdateTransactionCommand(id,transaction));
    }

    @DeleteMapping("/transaction/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
        return deleteTransactionService.execute(id);
    }
}
