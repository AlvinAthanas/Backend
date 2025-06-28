package com.example.cms_backend.Model.Entities;

import com.example.cms_backend.Model.Enums.PaymentMethod;
import com.example.cms_backend.Model.Enums.TransactionCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@Table(name = "financial_transaction")
public class FinancialTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    @Column(length = 2000)
    private String description;
    private Long amount;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private TransactionCategory category;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column(name = "parish_id")
    private Long parishId;

    @Column(name = "user_id")
    private Long userId;

    public FinancialTransaction(String type, String description, Long amount, LocalDate date) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date = date;

    }

    public FinancialTransaction() {
    }

}
