package com.example.cms_backend.services.TransactionServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Commands.GetExpenseTotalQuery;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.FinancialTransactionRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
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
