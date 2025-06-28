package com.example.cms_backend.Services.TransactionServices;


import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Exceptions.UserNotValidException;
import com.example.cms_backend.Model.Commands.TransactionCommand;
import com.example.cms_backend.Model.DTO.TransactionDTO;
import com.example.cms_backend.Model.Entities.FinancialTransaction;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.FinancialTransactionRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTransactionService implements Command<TransactionCommand, TransactionDTO> {
    private final FinancialTransactionRepository transactionRepository;
    private final UserRepository userRepository;



    @Override
    public ResponseEntity<TransactionDTO> execute(TransactionCommand command) {
        String email = LoggedInUserUtil.loggedInUserEmail(command.getRequest());
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        command.getTransaction().setUserId(user.getId());
        if (user.getParishId() != null) {
            command.getTransaction().setParishId(user.getParishId());
        } else {
            throw new UserNotValidException("User does not belong to a parish");
        }// Default to 0 if no parish is set;
        transactionRepository.save(command.getTransaction());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionDTO(command.getTransaction()));
    }
}
