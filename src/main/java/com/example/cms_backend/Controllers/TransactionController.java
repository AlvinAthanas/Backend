package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.GetExpenseTotalQuery;
import com.example.cms_backend.Model.Commands.TransactionCommand;
import com.example.cms_backend.Model.DTO.TransactionDTO;
import com.example.cms_backend.Model.Entities.FinancialTransaction;
import com.example.cms_backend.Model.Commands.UpdateTransactionCommand;
import com.example.cms_backend.Services.TransactionServices.*;
import jakarta.servlet.http.HttpServletRequest;
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
    private final CreateTransactionsService createTransactionsService;
    private final SearchTransactionService searchTransactionService;
    private final GetTotalExpenseService getTotalExpenseService;

    public TransactionController(CreateTransactionService createTransactionService,
                                 GetTransactionService getTransactionService,
                                 UpdateTransactionService updateTransactionService,
                                 DeleteTransactionService deleteTransactionService,
                                 GetTransactionsService getTransactionsService,
                                 CreateTransactionsService createTransactionsService,
                                 SearchTransactionService searchTransactionService,
                                 GetTotalExpenseService getTotalExpenseService) {
        this.createTransactionService = createTransactionService;
        this.getTransactionService = getTransactionService;
        this.updateTransactionService = updateTransactionService;
        this.deleteTransactionService = deleteTransactionService;
        this.getTransactionsService = getTransactionsService;
        this.createTransactionsService = createTransactionsService;
        this.searchTransactionService = searchTransactionService;
        this.getTotalExpenseService = getTotalExpenseService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody FinancialTransaction transaction, HttpServletRequest request){
        return createTransactionService.execute(new TransactionCommand(transaction, request));
    }

    @PostMapping("/transactions")
    public ResponseEntity<List<FinancialTransaction>> createTransactions(@RequestBody List<FinancialTransaction> transactions){
        return createTransactionsService.execute(transactions);
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<FinancialTransaction> getTransaction(@PathVariable Long id){
        return getTransactionService.execute(id);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<FinancialTransaction>> getTransactions(HttpServletRequest request){
        return getTransactionsService.execute(request);
    }

    @GetMapping("/transaction/search")
    public ResponseEntity<List<FinancialTransaction>> searchTransactionByType(@RequestParam String type){
        return searchTransactionService.execute(type);
    }



    @PutMapping("/transaction/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @RequestBody FinancialTransaction transaction){
        return updateTransactionService.execute(new UpdateTransactionCommand(id,transaction));
    }

    @DeleteMapping("/transaction/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
        return deleteTransactionService.execute(id);
    }

    @GetMapping("/total-expense")
    public ResponseEntity<Long> getTotalExpense(HttpServletRequest request) {
        return getTotalExpenseService.execute(new GetExpenseTotalQuery(request));
    }
}
