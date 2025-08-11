package com.github.navelogic.estudiovirtualapi.Service.Studio;

import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Enum.FinancialEntityTypeEnum;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model.Account;
import com.github.navelogic.estudiovirtualapi.Model.Player;
import com.github.navelogic.estudiovirtualapi.Model.Studio;
import com.github.navelogic.estudiovirtualapi.Repository.PlayerRepository;
import com.github.navelogic.estudiovirtualapi.Repository.StudioRepository;
import com.github.navelogic.estudiovirtualapi.Service.Studio.Util.MapToStudioResponseDTO;
import com.github.navelogic.estudiovirtualapi.Util.DTO.Studio.StudioRegistrationDTO;
import com.github.navelogic.estudiovirtualapi.Util.DTO.Studio.StudioResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateStudioUseCase {

    private final StudioRepository studioRepository;
    private final PlayerRepository playerRepository;
    private final MapToStudioResponseDTO mapToStudioResponseDTO;

    public StudioResponseDTO createForPlayer(StudioRegistrationDTO studioDTO, UUID ownerId) {

        Player owner = playerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Jogador com ID " + ownerId + " não encontrado."));

        studioRepository.findByOwnerId(ownerId).ifPresent(s -> {
            throw new IllegalStateException("Este jogador já possui um estúdio.");
        });

        var account = new Account();

        var newStudio = Studio.builder()
                .name(studioDTO.getName())
                .description(studioDTO.getDescription())
                .owner(owner)
                .isActive(true)
                .isAiControlled(false)
                .build();

        newStudio.setAccount(account);
        account.setStudioOwner(newStudio);
        account.setHolderType(FinancialEntityTypeEnum.STUDIO);

        var savedStudio = studioRepository.save(newStudio);
        return mapToStudioResponseDTO.execute(savedStudio);
    }
}
