package com.github.navelogic.estudiovirtualapi.Model;


import com.github.navelogic.estudiovirtualapi.Util.Enum.CrewRoleEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.GenderEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.GenreEnum;
import jakarta.persistence.*;
import lombok.*;

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

    /* Estava me dando muito trabalho para implementar personalidades. Farei no futuro.
    @Column(name = "personality_traits", columnDefinition = "TEXT")
    @Convert(converter = PersonalityTraitConverter.class)
    private Set<PersonalityTraitEnum> personalityTraits = new HashSet<>();
     */
    private Integer popularity;
    private Integer artisticCreativity;
    private Integer experience;
    private Integer productivity;
    private Integer stress;

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
