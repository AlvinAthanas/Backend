package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.Diocese;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DioceseRepository extends JpaRepository<Diocese, Long> {
    public List<Diocese> findDioceseByNameContaining(String name);
}
