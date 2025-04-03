package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findByNameContaining(String name);
    public boolean existsByEmail(String email);
    public Optional<User> findByEmail(String email);
}
