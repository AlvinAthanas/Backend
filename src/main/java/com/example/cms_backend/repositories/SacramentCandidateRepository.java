package com.example.cms_backend.repositories;

import com.example.cms_backend.model.Entities.SacramentCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SacramentCandidateRepository extends JpaRepository<SacramentCandidate, Long> {
}
