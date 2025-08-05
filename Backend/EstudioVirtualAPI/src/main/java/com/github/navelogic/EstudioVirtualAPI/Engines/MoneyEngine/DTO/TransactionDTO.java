package com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.DTO;

import com.github.navelogic.estudiovirtualapi.Util.Enum.TransactionCategoryEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Data Transfer Object (DTO) that represents a single financial transaction for client-side display.
 *
 * <p>This class serves as a "read model" for the {@link com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model.Transaction}
 * entity. It is used to send a simplified and curated view of a transaction's data to the API client,
 * suitable for rendering in lists, ledgers, or historical logs. It decouples the API representation
 * from the internal database model.</p>
 *
 * @author Navelogic
 * @since 2025-08-05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    /**
     * The unique identifier of the transaction.
     */
    private Long transactionId;

    /**
     * The monetary value of the transaction, represented as {@link BigDecimal} for precision.
     */
    private BigDecimal amount;

    /**
     * The type of the transaction (e.g., CREDIT or DEBIT), indicating the direction of the money flow.
     * @see TransactionTypeEnum
     */
    private TransactionTypeEnum type;

    /**
     * The business category of the transaction (e.g., SALARIES, REVENUE), providing context.
     * @see TransactionCategoryEnum
     */
    private TransactionCategoryEnum category;

    /**
     * A human-readable description of the transaction.
     */
    private String description;

    /**
     * The date on which the transaction occurred.
     */
    private LocalDate transactionDate;
}
