package com.github.navelogic.estudiovirtualapi.Repository;

import com.github.navelogic.estudiovirtualapi.Model.Finance.StudioFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioFinanceRepository extends JpaRepository<StudioFinance, Long> {
}
