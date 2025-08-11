package com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Repository;

import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Transaction} entity.
 *
 * <p>This interface provides the data access layer for {@code Transaction} objects.
 * It extends {@link JpaRepository} to inherit a standard set of CRUD and
 * pagination methods. Additionally, it defines custom query methods specific
 * to the application's needs.</p>
 *
 * <p>The Spring Data framework automatically implements this interface at runtime,
 * generating the necessary queries from the method signatures.</p>
 *
 * @see Transaction
 * @see JpaRepository
 * @author Navelogic
 * @since 2025-08-05
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Finds all transactions that are marked as public (i.e., where {@code isPrivate} is false).
     *
     * <p>This method leverages Spring Data JPA's query derivation mechanism. The framework
     * parses the method name and automatically generates a query equivalent to:
     * {@code "SELECT t FROM Transaction t WHERE t.isPrivate = false"}.</p>
     *
     * <p>The results are returned in a {@link Page} object, which includes the list of
     * transactions for the current page as well as metadata for pagination
     * (e.g., total number of pages, total elements).</p>
     *
     * @param pageable an object containing pagination and sorting information.
     * @return a {@link Page} of public {@link Transaction} objects.
     */
    Page<Transaction> findByIsPrivateFalse(Pageable pageable);

}
