package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.AdminVerificationKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminVerificationKeyRepository extends JpaRepository<AdminVerificationKey, Long> {
    Optional<AdminVerificationKey> findByKey(String key);
}
