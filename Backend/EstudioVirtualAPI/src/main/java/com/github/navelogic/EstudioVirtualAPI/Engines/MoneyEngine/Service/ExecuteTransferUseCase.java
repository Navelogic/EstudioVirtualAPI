package com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Service;

import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.DTO.TransferRequestDTO;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model.Account;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyTransferEngine.Service.TransferService;
import com.github.navelogic.estudiovirtualapi.Model.Player;
import com.github.navelogic.estudiovirtualapi.Model.Production.Production;
import com.github.navelogic.estudiovirtualapi.Repository.CrewMemberRepository;
import com.github.navelogic.estudiovirtualapi.Repository.PlayerRepository;
import com.github.navelogic.estudiovirtualapi.Repository.ProductionRepository;
import com.github.navelogic.estudiovirtualapi.Repository.StudioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case responsible for orchestrating the transfer of funds between accounts.
 *
 * <p>This class acts as a high-level orchestrator in the application's service layer.
 * Its primary responsibility is to translate a client's request (via {@link TransferRequestDTO})
 * into a concrete financial operation. It fetches the necessary entities (source player,
 * destination entity), validates their existence, and then delegates the low-level
 * financial logic to the {@link TransferService}.</p>
 *
 * @see TransferRequestDTO
 * @see TransferService
 *
 * @author Navelogic
 * @since 2025-08-05
 */
@Service
@RequiredArgsConstructor
public class ExecuteTransferUseCase {

    private final TransferService transferService;
    private final PlayerRepository playerRepository;
    private final StudioRepository studioRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final ProductionRepository productionRepository;

    /**
     * Executes the fund transfer operation.
     * <p>This method is the single entry point for this use case. It is marked as
     * {@code @Transactional} to ensure that the entire operation, including the
     * read and write operations performed by the underlying {@link TransferService},
     * is treated as a single, atomic unit.</p>
     *
     * @param dto The DTO containing the details of the transfer request.
     * @param sourceUserId The UUID of the player initiating the transfer.
     * @throws EntityNotFoundException if the source player or destination entity cannot be found.
     * @throws IllegalStateException if the source player does not have a financial account.
     */
    @Transactional
    public void execute(TransferRequestDTO dto, UUID sourceUserId) {
        Player sourcePlayer = playerRepository.findById(sourceUserId)
                .orElseThrow(() -> new EntityNotFoundException("Source player not found with ID: " + sourceUserId));

        Account sourceAccount = sourcePlayer.getAccount();
        if (sourceAccount == null) {
            throw new IllegalStateException("The source player does not have a financial account.");
        }

        Account destinationAccount = findDestinationAccount(dto);
        Production production = findProduction(dto.getProductionId());

        transferService.transferFunds(
                sourceAccount.getId(),
                destinationAccount.getId(),
                dto.getAmount(),
                dto.getCategory(),
                dto.getDescription(),
                production
        );
    }

    /**
     * A private helper method to find the destination account based on the DTO.
     * <p>This method encapsulates the logic for resolving the destination entity's ID
     * into a concrete {@link Account} object. It uses the {@code destinationType} from the DTO
     * to determine which repository to query.</p>
     *
     * @param dto The transfer request DTO.
     * @return The {@link Account} of the destination entity.
     * @throws EntityNotFoundException if the destination entity is not found.
     */
    private Account findDestinationAccount(TransferRequestDTO dto) {
        Account destinationAccount = switch (dto.getDestinationType()) {
            case PLAYER -> playerRepository.findById(UUID.fromString(dto.getDestinationId()))
                    .orElseThrow(() -> new EntityNotFoundException("Destination player not found."))
                    .getAccount();
            case STUDIO -> studioRepository.findById(Long.parseLong(dto.getDestinationId()))
                    .orElseThrow(() -> new EntityNotFoundException("Destination studio not found."))
                    .getAccount();
            case CREW_MEMBER -> crewMemberRepository.findById(Long.parseLong(dto.getDestinationId()))
                    .orElseThrow(() -> new EntityNotFoundException("Destination crew member not found."))
                    .getAccount();
        };

        if (destinationAccount == null) {
            throw new IllegalStateException("The destination entity " + dto.getDestinationId() + " does not have a financial account.");
        }
        return destinationAccount;
    }

    /**
     * A private helper method to find an optional associated production.
     *
     * @param productionId The ID of the production to find. Can be null.
     * @return The {@link Production} entity if found, otherwise null.
     */
    private Production findProduction(Long productionId) {
        if (productionId == null) return null;
        return productionRepository.findById(productionId).orElse(null);
    }

}
