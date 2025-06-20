package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.Kanda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KandaRepository extends JpaRepository<Kanda,Long> {
    List<Kanda> findByNameContainingIgnoreCase(String name);
    List<Kanda> findByParishId(Long parishId);
    List<Kanda> findByNameContainingIgnoreCaseAndParishId(String name, Long parishId);

}
