package com.example.cms_backend.repositories;

import com.example.cms_backend.model.Entities.Parish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParishRepository extends JpaRepository<Parish,Long> {
    List<Parish> findByNameContaining(String name);

}