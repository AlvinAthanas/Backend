package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByTitleContaining(String keyword);
}
