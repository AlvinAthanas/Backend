package com.example.cms_backend.repositories;

import com.example.cms_backend.model.Entities.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {

    // Optional: keep your original method
    List<Contribution> findContributionsByTypeContaining(String type);

    // Custom query to filter by type, month, and year
    @Query("SELECT c FROM Contribution c WHERE " +
            "(:type IS NULL OR c.type = :type) AND " +
            "(:month IS NULL OR FUNCTION('MONTH', c.date) = :month) AND " +
            "(:year IS NULL OR FUNCTION('YEAR', c.date) = :year)")
    List<Contribution> filterByTypeMonthYear(
            @Param("type") String type,
            @Param("month") Integer month,
            @Param("year") Integer year
    );

    // Find contributions by userId
    List<Contribution> findByUserId(Long userId);

    List<Contribution> findContributionByParishId(Long parishId);

    // Find contributions by userId and parishId
    List<Contribution> findByUserIdAndParishId(Long userId, Long parishId);

    // Total amount of contributions
    @Query("SELECT SUM(c.amount) FROM Contribution c")
    Long getTotalAmount();

    // Total amount of contributions filtered by parishId
    @Query("SELECT SUM(c.amount) FROM Contribution c WHERE c.parishId = :parishId")
    Long getTotalAmountByParishId(@Param("parishId") Long parishId);
}
