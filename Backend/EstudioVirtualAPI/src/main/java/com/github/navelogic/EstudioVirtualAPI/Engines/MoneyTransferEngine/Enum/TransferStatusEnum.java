package com.github.navelogic.estudiovirtualapi.Engines.MoneyTransferEngine.Enum;

/**
 * Represents the possible outcomes of a fund transfer operation.
 *
 * <p>This enum is designed to be used as a return type for service-layer methods
 * that execute transfers. Instead of throwing exceptions for predictable business rule
 * failures (like insufficient funds), a method can return a status from this enum.
 * This allows the calling code to handle different outcomes with more control using
 * a switch statement, a pattern often preferred for handling expected business scenarios.</p>
 *
 * @author Navelogic
 * @since 2025-08-05
 */
public enum TransferStatusEnum {
    /**
     * Indicates that the transfer was completed successfully.
     */
    TRANSFERRED,

    /**
     * Indicates that the transfer failed because the sending account could not be found or is invalid.
     */
    INVALID_SENDER,

    /**
     * Indicates that the transfer failed because the recipient account could not be found or is invalid.
     */
    INVALID_RECIPIENT,

    /**
     * Indicates that the transfer failed because the amount was invalid (e.g., zero or negative).
     */
    INVALID_AMOUNT,

    /**
     * Indicates that the transfer failed because the sending account had insufficient funds.
     */
    NO_FUNDS
}
