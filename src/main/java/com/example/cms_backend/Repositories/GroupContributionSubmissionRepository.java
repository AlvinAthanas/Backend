package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.GroupContributionSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupContributionSubmissionRepository extends JpaRepository<GroupContributionSubmission, Long> {
    List<GroupContributionSubmission> findByRequirementId(Long requirementId);
    @Query("SELECT SUM(s.amount) FROM GroupContributionSubmission s WHERE s.requirementId = :requirementId")
    Optional<Long> sumByRequirementId(@Param("requirementId") Long requirementId);

    List<GroupContributionSubmission> findByUserId(Long userId);

    @Query("SELECT SUM(s.amount) FROM GroupContributionSubmission s WHERE s.requirementId = :requirementId AND s.userId = :userId")
    Optional<Long> sumUserContribution(@Param("requirementId") Long requirementId, @Param("userId") Long userId);

}
