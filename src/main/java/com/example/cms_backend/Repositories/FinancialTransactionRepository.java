package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {

}
