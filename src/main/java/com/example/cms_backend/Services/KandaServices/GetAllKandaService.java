package com.example.cms_backend.Services.KandaServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Entities.Kanda;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.KandaRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAllKandaService implements Query<HttpServletRequest, List<Kanda>> {

    private final KandaRepository kandaRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<List<Kanda>> execute(HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();
        Long parishId = user.getParishId();

        if (parishId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Kanda> kandas = kandaRepository.findByParishId(parishId);
        return ResponseEntity.ok(kandas);
    }
}
