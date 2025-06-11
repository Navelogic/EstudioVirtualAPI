package com.github.navelogic.estudiovirtualapi.Repository;

import com.github.navelogic.estudiovirtualapi.Model.Production.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
