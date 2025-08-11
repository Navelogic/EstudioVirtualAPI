package com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Service;

import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.DTO.TransactionDTO;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A read-only use case responsible for fetching a paginated list of all public financial transactions.
 *
 * <p>This service provides a public-facing view of the game's economy, allowing players
 * to see significant financial events. It is designed for high performance by implementing
 * caching on its query results. Its sole responsibility is to query the database,
 * map the results to a data transfer object ({@link TransactionDTO}), and return them.</p>
 *
 * @see TransactionDTO
 * @see TransactionRepository
 * @author Navelogic
 * @since 2025-08-05
 */
@Service
@RequiredArgsConstructor
public class ListPublicTransactionsUseCase {

    private final TransactionRepository transactionRepository;

    /**
     * Retrieves a paginated list of all transactions where {@code isPrivate} is false.
     *
     * <p>This method is annotated with {@code @Cacheable("public_transactions")}. This means
     * that the result of the database query for a specific page and sort order will be
     * stored in a cache named "public_transactions". Subsequent identical requests will
     * be served directly from the cache, significantly reducing database load and improving
     * response times for frequently accessed data.</p>
     *
     * <p>The method performs a mapping from the internal {@code Transaction} entity to the
     * public-facing {@link TransactionDTO}, ensuring that the API layer is decoupled from
     * the persistence model.</p>
     *
     * @param pageable An object provided by Spring Web that contains pagination (page number, page size)
     * and sorting information from the client's request.
     * @return A {@link Page} containing {@link TransactionDTO} objects and pagination metadata.
     */
    @Cacheable("public_transactions")
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findAllPublic(Pageable pageable) {
        return transactionRepository.findByIsPrivateFalse(pageable)
                .map(t -> new TransactionDTO(
                        t.getId(),
                        t.getAmount(),
                        t.getType(),
                        t.getCategory(),
                        t.getDescription(),
                        t.getTransactionDate()
                ));
    }
}
