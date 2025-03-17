package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Parish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParishRepository extends JpaRepository<Parish,Long> {
}
