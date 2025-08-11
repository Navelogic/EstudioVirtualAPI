package com.github.navelogic.estudiovirtualapi.Engines.MoneyTransferEngine.Enum;

/**
 * Defines the fundamental nature of a financial transaction, indicating
 * whether it increases or decreases an account's balance.
 *
 * <p>This enum is critical for the core logic of the TransferService, as it
 * directly controls whether an amount is added to (CREDIT) or subtracted from (DEBIT)
 * an account's balance during a transaction.</p>
 *
 * @author Navelogic
 * @since 2025-08-05
 */
public enum TransactionTypeEnum {
    /**
     * Represents a debit, an outgoing transaction that decreases the account's balance.
     * This is typically used for expenses, payments, or transfers out.
     */
    DEBIT,

    /**
     * Represents a credit, an incoming transaction that increases the account's balance.
     * This is typically used for revenue, income, or transfers in.
     */
    CREDIT
}
