package com.github.navelogic.estudiovirtualapi.Repository;

import com.github.navelogic.estudiovirtualapi.Model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {
    List<Studio> findByIsAiControlled(boolean isAiControlled);

    Optional<Studio> findByOwnerId(UUID ownerId);
}
