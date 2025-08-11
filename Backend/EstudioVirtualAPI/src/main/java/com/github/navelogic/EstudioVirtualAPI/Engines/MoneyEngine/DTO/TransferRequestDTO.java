package com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.DTO;

import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Enum.FinancialEntityTypeEnum;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyTransferEngine.Enum.TransactionCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * A Data Transfer Object (DTO) that encapsulates the data required to request a fund transfer.
 *
 * <p>This class is used as the request body for API endpoints that initiate a transfer of funds
 * from the authenticated user's account to a specified destination account. It includes validation
 * annotations to ensure that the client provides all necessary and valid data before the request
 * is processed by the application's business logic.</p>
 *
 * @author Navelogic
 * @since 2025-08-05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDTO  {

    /**
     * The type of the entity receiving the funds (e.g., PLAYER, STUDIO).
     * This field is mandatory and acts as a discriminator for the {@code destinationId}.
     */
    @NotNull(message = "The destination type is required")
    private FinancialEntityTypeEnum destinationType;

    /**
     * The unique identifier of the destination entity, provided as a String.
     * The backend will parse this ID based on the {@code destinationType}
     * (e.g., as a UUID for a PLAYER, or as a Long for a STUDIO). This field cannot be blank.
     */
    @NotBlank(message = "The destination ID is required")
    private String destinationId;

    /**
     * The monetary amount to be transferred.
     * Must be a positive value. Using {@link BigDecimal} ensures financial precision.
     */
    @NotNull(message = "The amount is required")
    @Positive(message = "The amount must be positive")
    private BigDecimal amount;

    /**
     * The business category of the transaction. This is mandatory for reporting and tracking purposes.
     * @see TransactionCategoryEnum
     */
    @NotNull(message = "The category is required")
    private TransactionCategoryEnum category;

    /**
     * A mandatory, human-readable description of the transfer's purpose.
     */
    @NotBlank(message = "The description is required")
    private String description;

    /**
     * An optional identifier for a {@link com.github.navelogic.estudiovirtualapi.Model.Production.Production}
     * to associate this transfer with a specific project. Can be null.
     */
    private Long productionId;
}
