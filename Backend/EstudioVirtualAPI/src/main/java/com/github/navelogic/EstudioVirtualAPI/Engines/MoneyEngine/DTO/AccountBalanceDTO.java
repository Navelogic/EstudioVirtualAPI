package com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.DTO;

import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Enum.FinancialEntityTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * A Data Transfer Object (DTO) that represents the balance and ownership details of a financial account.
 *
 * <p>This DTO is designed as a "read model" to be sent to API clients. It provides a clean,
 * summarized view of an account's state, containing just the essential information needed
 * for display in a user interface, such as a player's wallet or a studio's financial overview.</p>
 *
 * @author Navelogic
 * @since 2025-08-05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceDTO {
    /**
     * The unique identifier of the account.
     */
    private UUID accountId;

    /**
     * The current monetary balance of the account, represented as {@link BigDecimal} for precision.
     */
    private BigDecimal balance;

    /**
     * The display name of the account's owner (e.g., a player's username or a studio's name).
     */
    private String ownerName;

    /**
     * The type of the entity that owns the account, as defined by {@link FinancialEntityTypeEnum}.
     * This helps the client UI to apply appropriate icons or formatting.
     */
    private FinancialEntityTypeEnum ownerType;
}
