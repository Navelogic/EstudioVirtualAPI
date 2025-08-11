package com.github.navelogic.estudiovirtualapi.Model;

import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model.Account;
import com.github.navelogic.estudiovirtualapi.Model.Production.Production;
import com.github.navelogic.estudiovirtualapi.Util.Audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Studio extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player owner;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Production> productions = new HashSet<>();

    @OneToOne(mappedBy = "studioOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Account account;

    private Boolean isActive = true;
    private Boolean isAiControlled = false;
}
