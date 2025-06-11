package com.github.navelogic.estudiovirtualapi.Model.Finance;

import com.github.navelogic.estudiovirtualapi.Util.Enum.TransactionCategoryEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studio_finance_id")
    private StudioFinance studioFinance;

    @ManyToOne
    @JoinColumn(name = "production_finance_id", nullable = true)
    private ProductionFinance productionFinance;

    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum type;

    @Enumerated(EnumType.STRING)
    private TransactionCategoryEnum category;

    private BigDecimal amount;

    private String description;
    private LocalDate transactionDate;

    @CreationTimestamp
    private LocalDate createdAt;

}
