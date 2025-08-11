package com.github.navelogic.estudiovirtualapi.Service.Player;

import com.github.navelogic.estudiovirtualapi.Model.Player;
import com.github.navelogic.estudiovirtualapi.Repository.PlayerRepository;
import com.github.navelogic.estudiovirtualapi.Service.Player.Util.MapToPlayerResponseDTO;
import com.github.navelogic.estudiovirtualapi.Service.Player.Util.ValidateEmailUniqueness;
import com.github.navelogic.estudiovirtualapi.Util.DTO.Player.PlayerResponseDTO;
import com.github.navelogic.estudiovirtualapi.Util.DTO.Player.PlayerUpdateDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdatePlayerUseCase {

    private final ReadPlayerUseCase readPlayersUseCase;
    private final ValidateEmailUniqueness validateEmailUniqueness;
    private final PasswordEncoder passwordEncoder;
    private final PlayerRepository playerRepository;
    private final MapToPlayerResponseDTO mapToPlayerResponseDTO;


    @Transactional
    public PlayerResponseDTO update(UUID playerById1, PlayerUpdateDTO playerUpdateDTO) {
        Player playerById = readPlayersUseCase.entity(playerById1);

        if (playerUpdateDTO.getUsername() != null && !playerUpdateDTO.getUsername().isBlank()) {
            playerById.setUsername(playerUpdateDTO.getUsername());
        }
        if (playerUpdateDTO.getEmail() != null && !playerUpdateDTO.getEmail().isBlank() && !playerUpdateDTO.getEmail().equals(playerById.getEmail())) {
            validateEmailUniqueness.execute(playerUpdateDTO.getEmail(), playerById1);
            playerById.setEmail(playerUpdateDTO.getEmail());
        }
        if (playerUpdateDTO.getPassword() != null && !playerUpdateDTO.getPassword().isBlank()) {
            playerById.setPassword(passwordEncoder.encode(playerUpdateDTO.getPassword()));
        }

        var updatedPlayer = playerRepository.save(playerById);
        return mapToPlayerResponseDTO.execute(updatedPlayer);
    }
}
