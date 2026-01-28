package com.example.cms_backend.repositories;

import com.example.cms_backend.model.Entities.Diocese;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DioceseRepository extends JpaRepository<Diocese, Long> {
    public List<Diocese> findDioceseByNameContaining(String name);
}
