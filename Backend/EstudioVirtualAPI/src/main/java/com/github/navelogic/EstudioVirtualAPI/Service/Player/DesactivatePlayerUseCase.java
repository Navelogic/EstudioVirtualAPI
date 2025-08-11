package com.github.navelogic.estudiovirtualapi.Service.Player;

import com.github.navelogic.estudiovirtualapi.Model.Player;
import com.github.navelogic.estudiovirtualapi.Repository.PlayerRepository;
import com.github.navelogic.estudiovirtualapi.Service.Player.Util.MapToPlayerResponseDTO;
import com.github.navelogic.estudiovirtualapi.Util.DTO.Player.PlayerResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DesactivatePlayerUseCase {

    private final PlayerRepository playerRepository;
    private final ReadPlayerUseCase readPlayerUseCase;
    private final MapToPlayerResponseDTO mapToUserResponseDTO;

    @Transactional
    public void deactivateUser(UUID playerId) {
        Player player = readPlayerUseCase.findOne(playerId);
        if (!player.isActive()) {
            throw new IllegalStateException("Jogador já está inativo.");
        }
        player.setActive(false);
        playerRepository.save(player);
    }

    @Transactional
    public PlayerResponseDTO reactivateUser(UUID playerId) {
        Player player = readPlayerUseCase.findOne(playerId);
        if (player.isActive()) {
            throw new IllegalStateException("Jogador já está ativo.");
        }
        player.setActive(true);
        Player reactivatedPlayer = playerRepository.save(player);
        return mapToUserResponseDTO.execute(reactivatedPlayer);
    }

}
