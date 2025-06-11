package com.github.navelogic.estudiovirtualapi.Repository;

import com.github.navelogic.estudiovirtualapi.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
}
