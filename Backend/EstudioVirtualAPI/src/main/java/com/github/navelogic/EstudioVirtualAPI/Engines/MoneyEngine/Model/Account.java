package com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model;

import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Enum.FinancialEntityTypeEnum;
import com.github.navelogic.estudiovirtualapi.Model.CrewMember;
import com.github.navelogic.estudiovirtualapi.Model.Player;
import com.github.navelogic.estudiovirtualapi.Model.Studio;
import com.github.navelogic.estudiovirtualapi.Util.Audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a central financial account within the game's Money Engine.
 *
 * <p>This entity acts as a universal wallet that can be associated with any game
 * entity capable of holding funds, such as a {@link Player}, a {@link Studio},
 * or a {@link CrewMember}. It employs a polymorphic association through mutually
 * exclusive foreign keys ({@code studioOwner}, {@code playerOwner}, {@code crewMemberOwner})
 * to link to its specific owner.</p>
 *
 * @author Navelogic
 * @since 2025-08-05
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"transactions", "studioOwner", "playerOwner", "crewMemberOwner"})
public class Account extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * The current monetary balance of the account.
     * Stored as {@link BigDecimal} to ensure financial precision and avoid floating-point errors.
     * Cannot be null, defaults to zero.
     */
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * A discriminator column that specifies the type of the entity owning this account.
     * This simplifies business logic by allowing for quick identification of the owner's type
     * without needing to check multiple nullable foreign key fields.
     * @see FinancialEntityTypeEnum
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FinancialEntityTypeEnum holderType;

    /**
     * A one-to-one, lazily-fetched relationship to a {@link Studio} owner.
     * This field is non-null only if the {@code holderType} is {@code STUDIO}.
     * It is mutually exclusive with {@code playerOwner} and {@code crewMemberOwner}.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_owner_id", unique = true)
    private Studio studioOwner;

    /**
     * A one-to-one, lazily-fetched relationship to a {@link Player} owner.
     * This field is non-null only if the {@code holderType} is {@code PLAYER}.
     * It is mutually exclusive with {@code studioOwner} and {@code crewMemberOwner}.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_owner_id", unique = true)
    private Player playerOwner;

    /**
     * A one-to-one, lazily-fetched relationship to a {@link CrewMember} owner.
     * This field is non-null only if the {@code holderType} is {@code CREW_MEMBER}.
     * It is mutually exclusive with {@code studioOwner} and {@code playerOwner}.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew_member_owner_id", unique = true)
    private CrewMember crewMemberOwner;

    /**
     * A flag indicating whether the account is currently active.
     * Inactive accounts cannot be part of any transaction. This can be used for game
     * mechanics like freezing accounts. Defaults to {@code true}.
     */
    @Column(nullable = false)
    private Boolean isActive = true;

    /**
     * A list of all transactions associated with this account.
     * This represents the account's ledger or statement.
     */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

}
