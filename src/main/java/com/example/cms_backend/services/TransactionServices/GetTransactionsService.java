package com.example.cms_backend.services.TransactionServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.exceptions.UserNotValidException;
import com.example.cms_backend.model.Entities.FinancialTransaction;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.FinancialTransactionRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTransactionsService implements Query<HttpServletRequest, List<FinancialTransaction>> {
    private final FinancialTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    
    @Override
    public ResponseEntity<List<FinancialTransaction>> execute(HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Long parishId;
        if (user.getParishId() != null) {
            parishId = user.getParishId();
        } else {
            throw new UserNotValidException("User does not belong to a parish");
        }
        List<FinancialTransaction> transactions = transactionRepository.findAllByParishId(parishId);
        return ResponseEntity.ok(transactions);
    }
}