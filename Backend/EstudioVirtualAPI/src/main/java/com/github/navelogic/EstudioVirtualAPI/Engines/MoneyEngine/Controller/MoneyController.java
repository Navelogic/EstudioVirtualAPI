package com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Controller;

import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.DTO.AccountBalanceDTO;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.DTO.TransactionDTO;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.DTO.TransferRequestDTO;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Service.ExecuteTransferUseCase;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Service.ListPublicTransactionsUseCase;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Service.ReadBalanceUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The REST controller that serves as the API gateway for all financial operations in the Money Engine.
 *
 * <p>This controller exposes endpoints for checking balances, transferring funds, and viewing
 * transaction histories. It follows the principle of a "thin controller" by delegating all
 * business logic to the appropriate use case services. Its primary responsibilities are
 * handling HTTP requests, validating input, managing authentication/authorization,
 * and serializing responses.</p>
 *
 * @author Navelogic
 * @since 2025-08-05
 */
@RestController
@RequestMapping("/api/v1/money")
@RequiredArgsConstructor
public class MoneyController {

    private final ReadBalanceUseCase readBalanceUseCase;
    private final ExecuteTransferUseCase executeTransferUseCase;
    private final ListPublicTransactionsUseCase listPublicTransactionsUseCase;


    /**
     * GET /api/v1/money/balance
     * <p>
     * Retrieves the balance of the currently authenticated player's personal account.
     * Requires authentication.
     *
     * @param userIdString The user ID injected by Spring Security from the authentication token.
     * @return A {@link ResponseEntity} with status 200 (OK) and an {@link AccountBalanceDTO} body.
     */
    @GetMapping("/balance")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AccountBalanceDTO> getMyBalance(@AuthenticationPrincipal String userIdString) {
        var userId = UUID.fromString(userIdString);
        return ResponseEntity.ok(readBalanceUseCase.getBalanceForUser(userId));
    }

    /**
     * GET /api/v1/money/balance/my-studio
     * <p>
     * Retrieves the balance of the studio account owned by the currently authenticated player.
     * Requires authentication.
     *
     * @param userIdString The user ID injected by Spring Security from the authentication token.
     * @return A {@link ResponseEntity} with status 200 (OK) and an {@link AccountBalanceDTO} body.
     */
    @GetMapping("/balance/my-studio")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AccountBalanceDTO> getMyStudioBalance(@AuthenticationPrincipal String userIdString) {
        var userId = UUID.fromString(userIdString);
        return ResponseEntity.ok(readBalanceUseCase.getBalanceForPlayerStudio(userId));
    }

    /**
     * POST /api/v1/money/transfer
     * <p>
     * Initiates a fund transfer from the authenticated player's account to a specified destination.
     * The request body must be a valid {@link TransferRequestDTO}.
     * Requires authentication.
     *
     * @param transferRequest The DTO containing the details of the transfer.
     * @param userIdString The user ID of the sender, injected by Spring Security.
     * @return A {@link ResponseEntity} with status 200 (OK) and an empty body upon success.
     */
    @PostMapping("/transfer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> transferFunds(
            @RequestBody @Valid TransferRequestDTO transferRequest,
            @AuthenticationPrincipal String userIdString) {
        var sourceUserId = UUID.fromString(userIdString);
        executeTransferUseCase.execute(transferRequest, sourceUserId);
        return ResponseEntity.ok().build();
    }

    /**
     * GET /api/v1/money/transactions/public
     * <p>
     * Retrieves a paginated list of all public financial transactions.
     * This endpoint is public and does not require authentication.
     *
     * @param pageable An object injected by Spring Web that contains pagination and sorting parameters
     * from the URL query string (e.g., ?page=0&size=20&sort=transactionDate,desc).
     * @return A {@link ResponseEntity} with status 200 (OK) and a {@link Page} of {@link TransactionDTO}s.
     */
    @GetMapping("/transactions/public")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<TransactionDTO>> listPublicTransactions(
            @PageableDefault(size = 20, sort = "transactionDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(listPublicTransactionsUseCase.findAllPublic(pageable));
    }

}
