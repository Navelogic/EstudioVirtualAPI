package com.github.navelogic.estudiovirtualapi.Service.Player;

import com.github.navelogic.estudiovirtualapi.Model.Player;
import com.github.navelogic.estudiovirtualapi.Repository.PlayerRepository;
import com.github.navelogic.estudiovirtualapi.Service.Player.Util.MapToPlayerResponseDTO;
import com.github.navelogic.estudiovirtualapi.Util.DTO.Player.PlayerResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadPlayerUseCase {

    private final PlayerRepository playerRepository;
    private final MapToPlayerResponseDTO mapToPlayerResponseDTO;

    /**
     * Retorna todos os jogadores ativos.
     *
     * @return Lista de jogadores ativos.
     */
    public List<PlayerResponseDTO> allActivated() {
        return playerRepository.findAllByIsActiveTrue().stream()
                .map(mapToPlayerResponseDTO::execute)
                .collect(Collectors.toList());
    }

    /**
     * Retorna todos os jogadores.
     *
     * @return Lista de todos os jogadores.
     */
    public List<PlayerResponseDTO> all() {
        return playerRepository.findAll().stream()
                .map(mapToPlayerResponseDTO::execute)
                .collect(Collectors.toList());
    }

    /**
     * Retorna um jogador específico pelo ID.
     *
     * @param userId ID do jogador a ser buscado.
     * @return Detalhes do jogador encontrado.
     * @throws EntityNotFoundException Se o jogador não for encontrado.
     */
    public PlayerResponseDTO one(UUID userId) {
        return playerRepository.findById(userId)
                .map(mapToPlayerResponseDTO::execute)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + userId));
    }

    public Player findOne(UUID userId) {
        return playerRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + userId));
    }

    public Player entity(UUID userId) {
        return playerRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + userId));
    }

}
