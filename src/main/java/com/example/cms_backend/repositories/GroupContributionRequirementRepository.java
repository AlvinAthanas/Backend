package com.example.cms_backend.repositories;

import com.example.cms_backend.model.Entities.GroupContributionRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupContributionRequirementRepository extends JpaRepository<GroupContributionRequirement, Long> {
    List<GroupContributionRequirement> findByGroupId(Long groupId);
}