package com.github.navelogic.estudiovirtualapi.Util.AIService.CrewMember;

import com.github.navelogic.estudiovirtualapi.Model.CrewMember;
import com.github.navelogic.estudiovirtualapi.Repository.CrewMemberRepository;
import com.github.navelogic.estudiovirtualapi.Util.Enum.CrewRoleEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.GenderEnum;
import com.github.navelogic.estudiovirtualapi.Util.NameAI.Service.CrewMemberNameAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CrewMemberCreateAIService {

    private final CrewMemberRepository crewMemberRepository;

    private final CrewMemberNameAIService crewMemberNameAIService;
    private final CrewMemberBirthDateService crewMemberBirthDateService;
    private final CrewMemberGenderService crewMemberGenderService;
    private final CrewMemberArtisticCreativity crewMemberArtisticCreativity;
    private final CrewMemberSpecialtyService crewMemberSpecialtyService;
    private final CrewMemberProductivityService crewMemberProductivityService;
    private final CrewMemberPopularityService crewMemberPopularityService;

    /**
     * Agendado para gerar e salvar um novo CrewMember em intervalos fixos.
     */
    @Transactional
    @Scheduled(fixedRate = 60000)
    public void scheduledCrewGeneration() {
        CrewMember crewMember = createRandomCrewMember();
        crewMemberRepository.save(crewMember);
    }

    /**
     * Orquestra a criação de uma instância de CrewMember.
     * @return Um objeto {@link CrewMember} pronto para ser persistido.
     */
    private CrewMember createRandomCrewMember() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        CrewRoleEnum role = CrewRoleEnum.values()[random.nextInt(CrewRoleEnum.values().length)];
        LocalDate birthDate = crewMemberBirthDateService.generateRandomBirthDate(role);
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        boolean isAdult = age >= 18;

        GenderEnum gender = crewMemberGenderService.generateGender(isAdult);
        String name = crewMemberNameAIService.generateCrewMemberName(String.valueOf(gender));

        int artisticCreativity = crewMemberArtisticCreativity.generateArtisticCreativity(role);
        int popularity = crewMemberPopularityService.generateInitialPopularity();
        int productivity = crewMemberProductivityService.generateProductivity();
        int experience = 0;

        return CrewMember.builder()
                .name(name)
                .gender(gender)
                .role(role)
                .birthDate(birthDate)
                .isAvailable(true)
                .isOnVacation(false)
                .isDead(false)
                .specialty(crewMemberSpecialtyService.generateSpecialty())
                .popularity(popularity)
                .artisticCreativity(artisticCreativity)
                .experience(experience)
                .productivity(productivity)
                .stress(0)
                .contracts(new HashSet<>())
                .deathDate(null)
                .deathCause(null)
                .vacationStartDate(null)
                .vacationEndDate(null)
                .build();
    }
}

