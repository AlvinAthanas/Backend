package com.example.cms_backend.Services.SacramentRegistrationServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Commands.CreateSacramentRegistrationCommand;
import com.example.cms_backend.Model.Entities.SacramentRegistration;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.SacramentRegistrationRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateSacramentRegistrationService implements Command<CreateSacramentRegistrationCommand, SacramentRegistration> {

    private final SacramentRegistrationRepository repository;
    private final UserRepository userRepository;

    public CreateSacramentRegistrationService(SacramentRegistrationRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<SacramentRegistration> execute(CreateSacramentRegistrationCommand command) {
        HttpServletRequest request = command.getRequest();
        String email = LoggedInUserUtil.loggedInUserEmail(request);

        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        SacramentRegistration reg = new SacramentRegistration();
        reg.setCandidateId(command.getUserId());
        reg.setSacramentType(command.getSacramentType());
        reg.setRegistrationDate(command.getRegistrationDate());
        reg.setStartDate(command.getStartDate());
        reg.setCompletionDate(command.getCompletionDate());
        reg.setCompleted(false);
        reg.setStatus("PENDING");
        reg.setParishId(user.getParishId());  // SET PARISH ID

        return ResponseEntity.ok(repository.save(reg));
    }
}
