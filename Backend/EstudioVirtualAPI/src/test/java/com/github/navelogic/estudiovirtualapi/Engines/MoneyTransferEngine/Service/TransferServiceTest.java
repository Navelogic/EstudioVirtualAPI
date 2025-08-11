package com.github.navelogic.estudiovirtualapi.Engines.MoneyTransferEngine.Service;

import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model.Account;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model.Transaction;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Repository.AccountRepository;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Repository.TransactionRepository;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyTransferEngine.Enum.TransactionCategoryEnum;
import com.github.navelogic.estudiovirtualapi.Util.Exception.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransferService transferService;

    private Account sourceAccount;
    private Account destinationAccount;
    private final UUID sourceAccountId = UUID.randomUUID();
    private final UUID destinationAccountId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        sourceAccount = new Account();
        sourceAccount.setId(sourceAccountId);
        sourceAccount.setBalance(new BigDecimal("1000.00"));

        destinationAccount = new Account();
        destinationAccount.setId(destinationAccountId);
        destinationAccount.setBalance(new BigDecimal("500.00"));
    }

    @Test
    @DisplayName("Deve creditar valor com sucesso e criar transação")
    void credit_shouldSucceedAndCreateTransaction() {
        when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));

        BigDecimal creditAmount = new BigDecimal("200.00");

        transferService.credit(sourceAccountId,
                creditAmount,
                TransactionCategoryEnum.REVENUE,
                "Test Credit",
                false,
                null);

        assertEquals(new BigDecimal("1200.00"), sourceAccount.getBalance());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(1)).save(sourceAccount);
    }

    @Test
    @DisplayName("Deve debitar valor com sucesso quando há fundos suficientes")
    void debit_shouldSucceedWhenFundsAreSufficient() {
        when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        BigDecimal debitAmount = new BigDecimal("300.00");

        transferService.debit(sourceAccountId,
                debitAmount,
                TransactionCategoryEnum.OPERATIONAL_COSTS,
                "Test Debit",
                false,
                null);

        assertEquals(new BigDecimal("700.00"), sourceAccount.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(1)).save(sourceAccount);
    }

    @Test
    @DisplayName("Deve lançar InsufficientFundsException quando não há fundos suficientes")
    void debit_shouldThrowExceptionWhenFundsAreInsufficient() {

        when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        BigDecimal debitAmount = new BigDecimal("1500.00"); // Valor maior que o saldo

        assertThrows(InsufficientFundsException.class, () -> {
            transferService.debit(sourceAccountId,
                    debitAmount,
                    TransactionCategoryEnum.OPERATIONAL_COSTS,
                    "Failed Debit",
                    false,
                    null);
        });

        verify(transactionRepository, never()).save(any());
        verify(accountRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve transferir fundos com sucesso entre duas contas")
    void transferFunds_shouldSucceed() {
        when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(destinationAccountId)).thenReturn(Optional.of(destinationAccount));

        BigDecimal transferAmount = new BigDecimal("500.00");

        transferService.transferFunds(sourceAccountId,
                destinationAccountId,
                transferAmount,
                TransactionCategoryEnum.INVESTMENT,
                "Test Transfer",
                null);

        assertEquals(new BigDecimal("500.00"), sourceAccount.getBalance());
        assertEquals(new BigDecimal("1000.00"), destinationAccount.getBalance());

        verify(transactionRepository, times(2)).save(any(Transaction.class));
        verify(accountRepository, times(2)).save(any(Account.class));
    }

    @Test
    @DisplayName("Deve capturar e verificar os detalhes das transações durante uma transferência")
    void transferFunds_shouldCaptureAndVerifyTransactionDetails() {

        when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(destinationAccountId)).thenReturn(Optional.of(destinationAccount));

        BigDecimal transferAmount = new BigDecimal("100.00");
        String description = "Payment for services";

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);

        transferService.transferFunds(sourceAccountId,
                destinationAccountId,
                transferAmount,
                TransactionCategoryEnum.SALARIES,
                description,
                null);

        verify(transactionRepository, times(2)).save(transactionCaptor.capture());

        Transaction debitTransaction = transactionCaptor.getAllValues().get(0);
        Transaction creditTransaction = transactionCaptor.getAllValues().get(1);

        assertEquals(sourceAccount, debitTransaction.getAccount());
        assertEquals(transferAmount, debitTransaction.getAmount());
        assertEquals(description, debitTransaction.getDescription());
        assertEquals(TransactionCategoryEnum.SALARIES, debitTransaction.getCategory());

        assertEquals(destinationAccount, creditTransaction.getAccount());
        assertEquals(transferAmount, creditTransaction.getAmount());
    }
}
