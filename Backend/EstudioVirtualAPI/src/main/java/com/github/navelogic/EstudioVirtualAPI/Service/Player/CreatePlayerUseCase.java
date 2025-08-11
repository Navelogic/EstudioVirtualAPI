package com.github.navelogic.estudiovirtualapi.Service.Player;

import com.github.navelogic.estudiovirtualapi.Model.Player;
import com.github.navelogic.estudiovirtualapi.Repository.PlayerRepository;
import com.github.navelogic.estudiovirtualapi.Service.Player.Util.MapToPlayerResponseDTO;
import com.github.navelogic.estudiovirtualapi.Service.Player.Util.ValidateEmailUniqueness;
import com.github.navelogic.estudiovirtualapi.Util.DTO.Player.PlayerRegistrationDTO;
import com.github.navelogic.estudiovirtualapi.Util.DTO.Player.PlayerResponseDTO;
import com.github.navelogic.estudiovirtualapi.Util.Enum.PlayerRoleEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePlayerUseCase {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final MapToPlayerResponseDTO mapToPlayerResponseDTO;
    private final ValidateEmailUniqueness validateEmailUniqueness;

    @Transactional
    public PlayerResponseDTO create(PlayerRegistrationDTO userDTO) {
        validateEmailUniqueness.execute(userDTO.getEmail(), null);

        var newPlayer = Player.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .userRole(PlayerRoleEnum.ROLE_USER)
                .isActive(true)
                .build();

        var savedPlayer = playerRepository.save(newPlayer);
        return mapToPlayerResponseDTO.execute(savedPlayer);
    }
}
