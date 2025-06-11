package com.github.navelogic.estudiovirtualapi.Repository;

import com.github.navelogic.estudiovirtualapi.Model.Production.Serie.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
}
