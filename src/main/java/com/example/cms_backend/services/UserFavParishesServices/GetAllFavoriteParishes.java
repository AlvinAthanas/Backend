package com.example.cms_backend.services.UserFavParishesServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Entities.Parish;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GetAllFavoriteParishes implements Query<Long, List<Parish>> {

    private final UserRepository userRepository;

    public GetAllFavoriteParishes(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Parish>> execute(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        if (user.getFavoriteParishes() == null) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        List<Parish> favoriteParishes = new ArrayList<>(user.getFavoriteParishes());
        return ResponseEntity.ok(favoriteParishes);
    }
}
