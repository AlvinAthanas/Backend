package com.example.cms_backend.Services.TransactionServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Commands.GetExpenseTotalQuery;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.FinancialTransactionRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetTotalExpenseService implements Query<GetExpenseTotalQuery, Long> {

    private final FinancialTransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public GetTotalExpenseService(FinancialTransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Long> execute(GetExpenseTotalQuery command) {
        HttpServletRequest request = command.getRequest();
        String email = LoggedInUserUtil.loggedInUserEmail(request);

        if (email == null) {
            throw new UserNotFoundException(); // Or custom exception for unauthenticated access
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        Long parishId = userOptional.get().getParishId();
        Long totalExpense = transactionRepository.sumByTypeAndParishId("EXPENSE", parishId);

        return ResponseEntity.ok(totalExpense != null ? totalExpense : 0L);
    }
}
