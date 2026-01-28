package com.example.cms_backend.services.KandaServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Commands.SearchKandaCommand;
import com.example.cms_backend.model.Entities.Kanda;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.KandaRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchKandaService implements Query<SearchKandaCommand, List<Kanda>> {

    private final KandaRepository kandaRepository;
    private final UserRepository userRepository;

    public SearchKandaService(KandaRepository kandaRepository, UserRepository userRepository) {
        this.kandaRepository = kandaRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Kanda>> execute(SearchKandaCommand command) {
        HttpServletRequest request = command.getRequest();
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        if (email == null) {
            return ResponseEntity.status(401).build();
        }

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        Long parishId = user.get().getParishId();

        List<Kanda> result;
        if (command.getName() != null) {
            result = kandaRepository.findByNameContainingIgnoreCaseAndParishId(command.getName(), parishId);
        } else {
            result = kandaRepository.findByParishId(parishId);
        }

        return ResponseEntity.ok(result);
    }
}
