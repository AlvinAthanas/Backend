package com.example.cms_backend.repositories;

import com.example.cms_backend.model.Entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    @Query("SELECT f FROM Feedback f WHERE f.parishId = :parishId AND (f.receiverId IS NULL OR f.receiverId = :receiverId)")
    List<Feedback> findByParishIdAndReceiverIdIsNullOrReceiverId(Long parishId, Long receiverId);
}
