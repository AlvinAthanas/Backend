package com.example.cms_backend.Services.UserFavParishesServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Commands.FavParishCommand;
import com.example.cms_backend.Model.Entities.Parish;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.ParishRepository;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AddOrRemoveFavoriteParishService implements Command<FavParishCommand, Boolean> {

    private final UserRepository userRepository;
    private final ParishRepository parishRepository;

    public AddOrRemoveFavoriteParishService(UserRepository userRepository, ParishRepository parishRepository) {
        this.userRepository = userRepository;
        this.parishRepository = parishRepository;
    }

    @Override
    public ResponseEntity<Boolean> execute(FavParishCommand input) {
        if (input == null || input.getUserId() == null || input.getParishId() == null) {
            return ResponseEntity.badRequest().body(false);
        }

        Optional<User> userOpt = userRepository.findById(input.getUserId());
        Optional<Parish> parishOpt = parishRepository.findById(input.getParishId());

        if (userOpt.isEmpty() || parishOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        User user = userOpt.get();
        Parish parish = parishOpt.get();

        boolean modified = false;

        if (input.getIsLiked()) {
            if (!user.getFavoriteParishes().contains(parish)) {
                user.getFavoriteParishes().add(parish);
                modified = true;
            }
        } else {
            if (user.getFavoriteParishes().contains(parish)) {
                user.getFavoriteParishes().remove(parish);
                modified = true;
            }
        }

        if (modified) {
            userRepository.save(user);
        }

        return ResponseEntity.ok(true);
    }
}

