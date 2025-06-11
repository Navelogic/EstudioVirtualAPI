package com.github.navelogic.estudiovirtualapi.Model.Finance;

import com.github.navelogic.estudiovirtualapi.Model.Studio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudioFinance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;

    private BigDecimal currentBudget = BigDecimal.valueOf(500000);
    private BigDecimal weeklyOperationalCosts = BigDecimal.ZERO;
    private BigDecimal marketValue = BigDecimal.ZERO;

    private BigDecimal averageROI = BigDecimal.ZERO;
    private BigDecimal averageProductionBudget = BigDecimal.ZERO;
    private Integer totalProductionsFinanced = 0;
    private Integer successfulProductions = 0;

    @OneToMany(mappedBy = "studioFinance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FinancialTransaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "studioFinance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductionFinance> productionFinances = new ArrayList<>();

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

}
