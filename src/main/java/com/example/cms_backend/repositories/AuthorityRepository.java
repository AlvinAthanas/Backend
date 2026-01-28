package com.example.cms_backend.repositories;

import com.example.cms_backend.model.Entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    public List<Authority> findByNameContaining(String name);

    Optional<Authority> findByName(String authorityName);
}