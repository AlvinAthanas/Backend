package com.example.cms_backend.Services.SacramentRegistrationServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Commands.GetSessionFilterCommand;
import com.example.cms_backend.Model.DTO.SacramentSessionInfo;
import com.example.cms_backend.Model.Enums.SacramentType;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.SacramentRegistrationRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetSacramentSessionsService implements Query<GetSessionFilterCommand, List<SacramentSessionInfo>> {

    private final SacramentRegistrationRepository repository;
    private final UserRepository userRepository;

    public GetSacramentSessionsService(SacramentRegistrationRepository repository,
                                       UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<SacramentSessionInfo>> execute(GetSessionFilterCommand command) {
        HttpServletRequest request = command.getRequest();
        String email = LoggedInUserUtil.loggedInUserEmail(request);

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return ResponseEntity.badRequest().build();

        Long parishId = optionalUser.get().getParishId();
        SacramentType type = command.getSacramentType();

        List<SacramentSessionInfo> sessions = repository.findDistinctSessionsByParishAndType(parishId, type);
        return ResponseEntity.ok(sessions);
    }
}
