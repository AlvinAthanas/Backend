package com.example.cms_backend.Services.UserFavParishesServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Parish;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
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
