package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByNameContaining(String name);

    List<Event> findByDescriptionContaining(String description);

    List<Event> findByParishId(Long parishId);
}
