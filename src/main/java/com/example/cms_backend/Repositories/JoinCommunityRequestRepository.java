package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.JoinCommunityRequest;
import com.example.cms_backend.Model.Enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinCommunityRequestRepository extends JpaRepository<JoinCommunityRequest, Long> {
    List<JoinCommunityRequest> findByUserId(Long userId);
    List<JoinCommunityRequest> findByGroupId(Long groupId);
    List<JoinCommunityRequest> findByStatus(RequestStatus status);
}
