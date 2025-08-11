package com.github.navelogic.estudiovirtualapi.Repository;

import com.github.navelogic.estudiovirtualapi.Model.Finance.InflationIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface InflationIndexRepository extends JpaRepository<InflationIndex, Long> {
    Optional<InflationIndex> findByReferenceDate(LocalDate referenceDate);
}
