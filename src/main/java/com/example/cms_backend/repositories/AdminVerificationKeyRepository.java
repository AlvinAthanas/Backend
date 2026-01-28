package com.example.cms_backend.repositories;

import com.example.cms_backend.model.Entities.AdminVerificationKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminVerificationKeyRepository extends JpaRepository<AdminVerificationKey, Long> {
    Optional<AdminVerificationKey> findByKey(String key);
}
