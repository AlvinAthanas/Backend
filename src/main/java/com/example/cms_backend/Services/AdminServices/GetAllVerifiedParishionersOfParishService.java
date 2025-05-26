package com.example.cms_backend.Services.AdminServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.AdminVerificationStatus;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Query:  Input  -> parishId (Long)
 *         Output -> List<UserDTO>   (verified PARISHIONERs of that parish)
 */
@Service
public class GetAllVerifiedParishionersOfParishService implements Query<Long, List<UserDTO>> {

    private final UserRepository userRepository;

    public GetAllVerifiedParishionersOfParishService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<UserDTO>> execute(Long parishId) {
        // 1️⃣  Fetch every user in the parish
        List<User> usersInParish = userRepository.findAllByParishId(parishId);

        // 2️⃣  Keep only VERIFIED users whose roles include PARISHIONER
        List<UserDTO> verifiedParishioners = usersInParish.stream()
                .filter(u -> u.getAdminVerificationStatus() == AdminVerificationStatus.VERIFIED)
                .filter(u -> u.getRoles() != null)
                .filter(u -> u.getRoles()
                        .stream()
                        .anyMatch(r -> r.getName().equalsIgnoreCase("PARISHIONER")))
                .map(UserDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(verifiedParishioners);
    }
}
