package com.example.cms_backend.repositories;

import com.example.cms_backend.model.Entities.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {
    List<FinancialTransaction> findFinancialTransactionsByTypeContaining(String type);
    List<FinancialTransaction> findAllByParishId(Long parishId);

    @Query("SELECT SUM(ft.amount) FROM FinancialTransaction ft WHERE ft.type = :type AND ft.parishId = :parishId")
    Long sumByTypeAndParishId(String type, Long parishId);
}
