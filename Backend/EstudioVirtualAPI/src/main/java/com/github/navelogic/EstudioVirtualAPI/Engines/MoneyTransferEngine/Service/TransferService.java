package com.github.navelogic.estudiovirtualapi.Engines.MoneyTransferEngine.Service;

import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model.Account;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model.Transaction;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Repository.AccountRepository;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Repository.TransactionRepository;
import com.github.navelogic.estudiovirtualapi.Model.Production.Production;
import com.github.navelogic.estudiovirtualapi.Util.Enum.TransactionCategoryEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.TransactionTypeEnum;
import com.github.navelogic.estudiovirtualapi.Util.Exception.InsufficientFundsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A low-level service responsible for executing the core financial operations of
 * crediting, debiting, and transferring funds between accounts.
 *
 * <p>This class contains the fundamental business logic for all monetary movements.
 * It ensures that operations are transactional, balances are updated correctly,
 * and every action is recorded as an immutable {@link Transaction}. It is designed
 * to be called by higher-level use case services (like {@code ExecuteTransferUseCase})
 * which orchestrate the workflow.</p>
 *
 * @author Navelogic
 * @since 2025-08-05
 */
@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Transfers funds from a source account to a destination account.
     * <p>
     * This operation is transactional. It is composed of a debit from the source
     * and a credit to the destination. If either step fails, the entire operation
     * is rolled back.
     *
     * @param sourceAccountId The ID of the account to debit from.
     * @param destinationAccountId The ID of the account to credit to.
     * @param amount The value to be transferred.
     * @param category The business category of the transaction.
     * @param description A description for both resulting transactions.
     * @param production An optional {@link Production} to associate with the transfer.
     */
    @Transactional
    public void transferFunds(UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount,
                              TransactionCategoryEnum category, String description, Production production) {
        debit(sourceAccountId, amount, category, description, false, production);
        credit(destinationAccountId, amount, category, description, false, production);
    }

    /**
     * Credits a specified amount to a single account.
     *
     * @param accountId The ID of the account to be credited.
     * @param amount The value to be credited.
     * @param category The category of the transaction.
     * @param description A description of the transaction.
     * @param isPrivate A flag indicating if the transaction should be private.
     * @param production An optional {@link Production} to associate with this transaction.
     */
    @Transactional
    public void credit(UUID accountId, BigDecimal amount, TransactionCategoryEnum category,
                       String description, boolean isPrivate, Production production) {
        applyTransaction(accountId, amount, TransactionTypeEnum.CREDIT, category, description, isPrivate, production);
    }

    /**
     * Debits a specified amount from a single account.
     *
     * @param accountId The ID of the account to be debited.
     * @param amount The value to be debited.
     * @param category The category of the transaction.
     * @param description A description of the transaction.
     * @param isPrivate A flag indicating if the transaction should be private.
     * @param production An optional {@link Production} to associate with this transaction.
     * @throws InsufficientFundsException if the account balance is less than the amount.
     */
    @Transactional
    public void debit(UUID accountId, BigDecimal amount, TransactionCategoryEnum category,
                      String description, boolean isPrivate, Production production) {
        applyTransaction(accountId, amount, TransactionTypeEnum.DEBIT, category, description, isPrivate, production);
    }

    /**
     * The central, private logic for processing any single financial transaction.
     * <p>
     * This method handles fetching the account, validating the amount, updating the balance
     * based on the transaction type, and creating the immutable transaction record.
     *
     * @param accountId The ID of the account to apply the transaction to.
     * @param amount The value of the transaction.
     * @param type The type of transaction (CREDIT or DEBIT).
     * @param category The category of the transaction.
     * @param description A description of the transaction.
     * @param isPrivate A flag for the transaction's visibility.
     * @param production An optional associated production.
     */
    private void applyTransaction(UUID accountId, BigDecimal amount, TransactionTypeEnum type,
                                  TransactionCategoryEnum category, String description, boolean isPrivate, Production production) {

        Objects.requireNonNull(amount, "The transaction amount cannot be null.");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The transaction amount must be positive.");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountId + " not found."));

        if (type == TransactionTypeEnum.CREDIT) {
            account.setBalance(account.getBalance().add(amount));
        } else {
            if (account.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException(String.format(
                        "Insufficient funds in account %s. Attempted to debit %.2f, current balance: %.2f.",
                        accountId, amount, account.getBalance()));
            }
            account.setBalance(account.getBalance().subtract(amount));
        }

        Transaction transaction = Transaction.builder()
                .account(account)
                .production(production)
                .amount(amount)
                .type(type)
                .category(category)
                .description(description)
                .isPrivate(isPrivate)
                .transactionDate(LocalDate.now())
                .build();

        transactionRepository.save(transaction);
        accountRepository.save(account);
    }
}

