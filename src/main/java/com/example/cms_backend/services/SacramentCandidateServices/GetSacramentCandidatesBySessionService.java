package com.example.cms_backend.services.SacramentCandidateServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Commands.GetSessionFilterCommand;
import com.example.cms_backend.model.Entities.SacramentCandidate;
import com.example.cms_backend.model.Entities.SacramentRegistration;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.SacramentCandidateRepository;
import com.example.cms_backend.repositories.SacramentRegistrationRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GetSacramentCandidatesBySessionService implements Query<GetSessionFilterCommand, List<SacramentCandidate>> {

    private final SacramentRegistrationRepository registrationRepository;
    private final SacramentCandidateRepository candidateRepository;
    private final UserRepository userRepository;

    public GetSacramentCandidatesBySessionService(SacramentRegistrationRepository registrationRepository,
                                                  SacramentCandidateRepository candidateRepository,
                                                  UserRepository userRepository) {
        this.registrationRepository = registrationRepository;
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<SacramentCandidate>> execute(GetSessionFilterCommand command) {
        HttpServletRequest request = command.getRequest();
        Optional<User> userOptional = userRepository.findByEmail(LoggedInUserUtil.loggedInUserEmail(request));
        Long userParishId = null;
        if (userOptional.isPresent()) {
            userParishId = userOptional.get().getParishId();
        }

        List<SacramentRegistration> filtered = registrationRepository.findByParishIdAndSacramentTypeAndStartDateAndCompletionDate(
                userParishId,
                command.getSacramentType(),
                command.getStartDate(),
                command.getCompletionDate()
        );

        List<Long> candidateIds = filtered.stream()
                .map(SacramentRegistration::getCandidateId)
                .distinct()
                .collect(Collectors.toList());

        List<SacramentCandidate> candidates = candidateRepository.findAllById(candidateIds);
        return ResponseEntity.ok(candidates);
    }
}
