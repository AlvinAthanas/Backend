package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.Feedback;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    List<Feedback> findByReceiverIdIsNullOrReceiverId(Long receiverId);
}
