package com.example.cms_backend.services.TransactionServices;


import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.exceptions.UserNotValidException;
import com.example.cms_backend.model.Commands.TransactionCommand;
import com.example.cms_backend.model.DTO.TransactionDTO;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.FinancialTransactionRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
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
