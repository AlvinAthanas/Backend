package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {
    public List<FinancialTransaction> findFinancialTransactionsByTypeContaining(String type);
    List<FinancialTransaction> findAllByParishId(Long parishId);
}
