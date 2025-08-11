package com.github.navelogic.estudiovirtualapi.Service.Player.Util;

import com.github.navelogic.estudiovirtualapi.Model.Player;
import com.github.navelogic.estudiovirtualapi.Util.DTO.Player.PlayerResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class MapToPlayerResponseDTO {

    /**
     * Maps a Player entity to a PlayerResponseDTO.
     *
     * @param user the Player entity to map
     * @return a PlayerResponseDTO containing the mapped data
     */
    public PlayerResponseDTO execute(Player user) {
        return PlayerResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.isActive())
                .role(user.getUserRole())
                .build();
    }

}
