package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    public List<Contribution> findContributionsByTypeContaining(String type);
}
