package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.Parish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParishRepository extends JpaRepository<Parish,Long> {
    public List<Parish> findByNameContaining(String name);
}
