package com.github.navelogic.estudiovirtualapi.Repository;

import com.github.navelogic.estudiovirtualapi.Model.Finance.ProductionFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionFinanceRepository extends JpaRepository<ProductionFinance, Long> {
}
