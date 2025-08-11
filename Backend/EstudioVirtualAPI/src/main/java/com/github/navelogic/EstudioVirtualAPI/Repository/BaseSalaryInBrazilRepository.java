package com.github.navelogic.estudiovirtualapi.Repository;

import com.github.navelogic.estudiovirtualapi.Model.Finance.BaseSalaryInBrazil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseSalaryInBrazilRepository extends JpaRepository<BaseSalaryInBrazil, Long> {
    Optional<BaseSalaryInBrazil> findByYearAndMonth(Integer year, Integer month);
}
