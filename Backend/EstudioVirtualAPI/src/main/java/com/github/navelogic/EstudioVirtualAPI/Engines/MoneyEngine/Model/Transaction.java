package com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model;

import com.github.navelogic.estudiovirtualapi.Model.Production.Production;
import com.github.navelogic.estudiovirtualapi.Util.Audit.Auditable;
import com.github.navelogic.estudiovirtualapi.Util.Enum.TransactionCategoryEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a single, immutable financial event within the Money Engine.
 *
 * <p>Each transaction is a record of a monetary movement (either a credit or a debit)
 * against a specific {@link Account}. It serves as a ledger entry, providing a complete
 * and auditable history of all financial activities. Once created, a transaction
 * should not be altered; to correct an error, a new, counteracting transaction
 * should be issued.</p>
 *
 * @author Navelogic
 * @since 2025-08-05
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"account", "production"})
public class Transaction extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The {@link Account} to which this transaction belongs. This is a mandatory,
     * many-to-one relationship, as every transaction must be associated with an account.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    /**
     * An optional link to a specific {@link Production}.
     * This allows for granular cost and revenue tracking for individual game projects,
     * such as attributing an expense to a film's marketing budget.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_id")
    private Production production;

    /**
     * The fundamental type of the transaction, indicating whether it is an
     * income (CREDIT) or an expense (DEBIT) for the associated account.
     * @see TransactionTypeEnum
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionTypeEnum type;

    /**
     * A category that provides context for the transaction, used for filtering,
     * reporting, and game logic (e.g., 'SALARIES', 'REVENUE', 'OPERATIONAL_COSTS').
     * @see TransactionCategoryEnum
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionCategoryEnum category;

    /**
     * The monetary value of the transaction.
     * Stored as {@link BigDecimal} for financial precision. This value is always positive;
     * the {@code type} field determines whether it's added or subtracted from the balance.
     */
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    /**
     * A human-readable description of the transaction for display in logs or UIs.
     * E.g., "Pagamento de salário para John Doe - Agosto de 2025".
     */
    private String description;

    /**
     * The date on which the transaction occurred.
     */
    @Column(nullable = false)
    private LocalDate transactionDate;

    /**
     * A flag to control the visibility of the transaction.
     * Public transactions ({@code isPrivate = false}) can be viewed by other players
     * in a public ledger, while private ones are only visible to the account holder.
     */
    private boolean isPrivate;
}
