package com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Repository;

import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the {@link Account} entity.
 *
 * <p>This interface provides the data access layer for {@code Account} objects.
 * By extending {@link JpaRepository}, it inherits a comprehensive set of standard
 * CRUD (Create, Read, Update, Delete) and pagination/sorting methods out of the box,
 * significantly reducing the need for boilerplate data access code.</p>
 *
 * <p>The Spring Data framework will automatically provide the implementation for this
 * interface at runtime.</p>
 *
 * @see Account
 * @see JpaRepository
 * @author Navelogic
 * @since 2025-08-05
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
