package com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Enum;

/**
 * Defines the types of entities that can participate in the financial system,
 * either by owning an Account or by being the target of a transaction.
 *
 * <p>This enum serves a dual purpose:
 * <ol>
 * <li>As a <b>discriminator</b> in the {@code Account} entity to identify the owner's type.</li>
 * <li>As a <b>type specifier</b> in DTOs (e.g., {@code TransferRequestDTO}) to indicate the
 * recipient's type in an API call.</li>
 * </ol>
 * This unification simplifies the codebase and provides a single source of truth for all
 * financially-involved entities.
 * </p>
 *
 * @author Navelogic
 */
public enum FinancialEntityTypeEnum {
    /**
     * Represents a {@link com.github.navelogic.estudiovirtualapi.Model.Studio}.
     */
    STUDIO,

    /**
     * Represents a {@link com.github.navelogic.estudiovirtualapi.Model.Player}.
     */
    PLAYER,

    /**
     * Represents a {@link com.github.navelogic.estudiovirtualapi.Model.CrewMember}.
     */
    CREW_MEMBER

    // PERSON
}
