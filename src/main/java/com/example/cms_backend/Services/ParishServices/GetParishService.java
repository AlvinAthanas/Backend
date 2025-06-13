package com.example.cms_backend.Services.ParishServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.ParishNotFoundException;
import com.example.cms_backend.Model.Commands.GetParishCommand;
import com.example.cms_backend.Model.Entities.Parish;
import com.example.cms_backend.Repositories.ParishRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetParishService implements Query<GetParishCommand, Parish> {
    private final ParishRepository parishRepository;
    private final UserRepository userRepository;

    public GetParishService(ParishRepository parishRepository, UserRepository userRepository) {
        this.parishRepository = parishRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Parish> execute(GetParishCommand command) {
        Long parishId = command.getId();

        if (parishId == null) {
            // Extract email from token
            String email = LoggedInUserUtil.loggedInUserEmail(command.getRequest());
            if (email == null) {
                throw new ParishNotFoundException(); // or AuthenticationException
            }

            // Look up the user
            var user = userRepository.findByEmail(email)
                    .orElseThrow(ParishNotFoundException::new);

            parishId = user.getParishId();
        }

        return parishRepository.findById(parishId)
                .map(ResponseEntity::ok)
                .orElseThrow(ParishNotFoundException::new);
    }
}
