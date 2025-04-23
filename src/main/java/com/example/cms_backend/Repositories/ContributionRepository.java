package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.Contribution;
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
}
