package com.github.navelogic.estudiovirtualapi.Model.Finance;

import com.github.navelogic.estudiovirtualapi.Model.Production.Production;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "production_finances")
public class ProductionFinance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "production_id")
    private Production production;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_finance_id")
    private StudioFinance studioFinance;

    private BigDecimal productionBudget = BigDecimal.ZERO;
    private BigDecimal marketingBudget = BigDecimal.ZERO;
    private BigDecimal totalBudgetUsed = BigDecimal.ZERO;
    private BigDecimal boxOfficeRevenue = BigDecimal.ZERO;
    private BigDecimal streamingRevenue = BigDecimal.ZERO;
    private BigDecimal merchandisingRevenue = BigDecimal.ZERO;
    private BigDecimal totalRevenue = BigDecimal.ZERO;
    private BigDecimal roi = BigDecimal.ZERO;
    private BigDecimal profit = BigDecimal.ZERO;
    private Boolean isProfitable = false;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;
}
