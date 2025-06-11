package com.github.navelogic.estudiovirtualapi.Model;

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
public class CrewMember {

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

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "crew_member_traits", joinColumns = @JoinColumn(name = "crew_member_id"))
    @Enumerated(EnumType.STRING)
    private Set<PersonalityTraitEnum> personalityTraits = new HashSet<>();

    private Integer popularity;
    private Integer artisticCreativity;
    private Integer experience;
    private Integer productivity;
    private Integer stress;

    private Boolean isDead = false;
    private Boolean isOnVacation = false;
    private Boolean isAvailable = true;

    private LocalDate birthDate;
    private LocalDate deathDate;
    private LocalDate vacationStartDate;
    private LocalDate vacationEndDate;

    private BigDecimal baseSalary;

    @OneToMany(mappedBy = "crewMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contract> contracts = new HashSet<>();

}
