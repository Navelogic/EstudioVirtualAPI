package com.github.navelogic.estudiovirtualapi.Model;


import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model.Account;
import com.github.navelogic.estudiovirtualapi.Model.Contract.Contract;
import com.github.navelogic.estudiovirtualapi.Util.Audit.Auditable;
import com.github.navelogic.estudiovirtualapi.Util.Enum.CrewRoleEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.GenderEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.GenreEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.PersonalityTraitEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrewMember extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Enumerated(EnumType.STRING)
    private CrewRoleEnum role;

    @Enumerated(EnumType.STRING)
    private GenreEnum specialty;

    @OneToOne(mappedBy = "crewMemberOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Account account;

    @Column(name = "personality_traits", columnDefinition = "TEXT")
    private Set<PersonalityTraitEnum> personalityTraits = new HashSet<>();

    private Integer popularity;
    private Integer artisticCreativity;
    private Integer experience;
    private Integer productivity;
    private Integer stress;

    private BigDecimal baseSalary = BigDecimal.ZERO;

    @Column(nullable = true)
    private String deathCause;

    private Boolean isDead = false;
    private Boolean isOnVacation = false;
    private Boolean isAvailable = true;

    private LocalDate birthDate;
    private LocalDate deathDate;
    private LocalDate vacationStartDate;
    private LocalDate vacationEndDate;

    @OneToMany(mappedBy = "crewMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contract> contracts = new HashSet<>();
}
