package com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Service;

import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.DTO.AccountBalanceDTO;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Enum.FinancialEntityTypeEnum;
import com.github.navelogic.estudiovirtualapi.Model.Player;
import com.github.navelogic.estudiovirtualapi.Model.Studio;
import com.github.navelogic.estudiovirtualapi.Repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * A read-only use case responsible for retrieving the balance of accounts
 * associated with a specific user.
 *
 * <p>This service provides methods to fetch the balance of a player's personal account
 * as well as the balance of the studio owned by that player. It encapsulates the logic
 * for finding the correct entities and mapping their financial data into a user-friendly
 * DTO ({@link AccountBalanceDTO}).</p>
 *
 * @see AccountBalanceDTO
 * @author Navelogic
 * @since 2025-08-05
 */
@Service
@RequiredArgsConstructor
public class ReadBalanceUseCase {

    private final PlayerRepository playerRepository;

    /**
     * Retrieves the balance of the personal account for a given user.
     *
     * @param userId The unique identifier of the user whose balance is being requested.
     * @return An {@link AccountBalanceDTO} containing the account details. If the user
     * does not yet have an account, a DTO with a zero balance is returned.
     */
    @Transactional(readOnly = true)
    public AccountBalanceDTO getBalanceForUser(UUID userId) {
        Player user = findUser(userId);
        if (user.getAccount() == null) {
            return new AccountBalanceDTO(null, BigDecimal.ZERO, user.getUsername(), FinancialEntityTypeEnum.PLAYER);
        }
        return new AccountBalanceDTO(
                user.getAccount().getId(),
                user.getAccount().getBalance(),
                user.getUsername(),
                FinancialEntityTypeEnum.PLAYER
        );
    }

    /**
     * Retrieves the balance of the studio account owned by a given user.
     *
     * @param userId The unique identifier of the user who owns the studio.
     * @return An {@link AccountBalanceDTO} containing the studio's account details.
     * @throws EntityNotFoundException if the specified user does not own a studio.
     */
    @Transactional(readOnly = true)
    public AccountBalanceDTO getBalanceForPlayerStudio(UUID userId) {
        Player user = findUser(userId);
        Studio studio = user.getStudio();

        if (studio == null) {
            throw new EntityNotFoundException("This player does not own a studio.");
        }
        if (studio.getAccount() == null) {
            return new AccountBalanceDTO(null, BigDecimal.ZERO, studio.getName(), FinancialEntityTypeEnum.STUDIO);
        }
        return new AccountBalanceDTO(
                studio.getAccount().getId(),
                studio.getAccount().getBalance(),
                studio.getName(),
                FinancialEntityTypeEnum.STUDIO
        );
    }

    /**
     * A private helper method to find a user by their ID, providing a consistent
     * error message if the user is not found.
     *
     * @param userId The UUID of the user to find.
     * @return The found {@link Player} entity.
     * @throws EntityNotFoundException if no user with the given ID exists.
     */
    private Player findUser(UUID userId) {
        return playerRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));
    }
}
