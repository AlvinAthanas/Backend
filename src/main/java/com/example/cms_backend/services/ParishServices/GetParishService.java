package com.example.cms_backend.services.ParishServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.ParishNotFoundException;
import com.example.cms_backend.model.Commands.GetParishCommand;
import com.example.cms_backend.model.Entities.Parish;
import com.example.cms_backend.repositories.ParishRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
