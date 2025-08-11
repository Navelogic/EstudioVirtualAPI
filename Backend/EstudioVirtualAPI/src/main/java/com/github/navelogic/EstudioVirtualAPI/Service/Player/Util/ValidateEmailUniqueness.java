package com.github.navelogic.estudiovirtualapi.Service.Player.Util;

import com.github.navelogic.estudiovirtualapi.Repository.PlayerRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValidateEmailUniqueness {

    private final PlayerRepository playerRepository;

    /**
     * Verifica se o email já está cadastrado, exceto para o jogador atual.
     *
     * @param email            O email a ser verificado.
     * @param currentPlayerId  O ID do jogador atual, para evitar conflito com ele mesmo.
     * @throws EntityExistsException Se o email já estiver cadastrado por outro jogador.
     */
    public void execute(String email, UUID currentPlayerId) {
        playerRepository.findByEmail(email).ifPresent(p -> {
            if (!p.getId().equals(currentPlayerId)) {
                throw new EntityExistsException("Email já cadastrado: " + email);
            }
        });
    }

}
