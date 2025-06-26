package com.github.navelogic.estudiovirtualapi.Util.AIService.CrewMember;

import com.github.navelogic.estudiovirtualapi.Util.Enum.CrewRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Serviço responsável por gerar e validar datas de nascimento para membros da equipe (CrewMember).
 */
@Service
@RequiredArgsConstructor
public class CrewMemberBirthDateService {

    private static final double CHILD_ACTOR_PROBABILITY = 0.15;
    private static final double EDUCATED_PROFESSIONAL_PROBABILITY = 0.80;

    /**
     * Gera uma data de nascimento aleatória e realista para um determinado papel na equipe.
     *
     * @param role A função do membro da equipe (ex: ATOR, DIRETOR).
     * @return Uma {@link LocalDate} representando a data de nascimento gerada.
     */
    public LocalDate generateRandomBirthDate(CrewRoleEnum role) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        AgeDistributionParameters params = getAgeDistributionParametersForRole(role, random);

        double betaSample = sampleBeta(params.alpha(), params.beta(), random);
        int ageRange = params.maxAge() - params.minAge();
        int generatedAge = params.minAge() + (int) Math.round(betaSample * ageRange);

        int age = Math.max(params.minAge(), Math.min(generatedAge, params.maxAge()));

        int birthYear = LocalDate.now().minusYears(age).getYear();
        int daysInYear = Year.isLeap(birthYear) ? 366 : 365;
        int randomDayOfYear = random.nextInt(1, daysInYear + 1);

        return LocalDate.ofYearDay(birthYear, randomDayOfYear);
    }

    /**
     * Gera uma data de nascimento considerando se o profissional possui educação formal.
     * Profissionais com educação formal terão idades mínimas baseadas no tempo necessário
     * para completar sua formação.
     *
     * @param role A função do membro da equipe.
     * @param hasEducation Se o profissional possui educação formal.
     * @return Uma {@link LocalDate} representando a data de nascimento gerada.
     */
    public LocalDate generateEducationAwareBirthDate(CrewRoleEnum role, boolean hasEducation) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        AgeDistributionParameters params = getEducationAwareAgeDistribution(role, hasEducation, random);

        double betaSample = sampleBeta(params.alpha(), params.beta(), random);
        int ageRange = params.maxAge() - params.minAge();
        int generatedAge = params.minAge() + (int) Math.round(betaSample * ageRange);

        int age = Math.max(params.minAge(), Math.min(generatedAge, params.maxAge()));

        int birthYear = LocalDate.now().minusYears(age).getYear();
        int daysInYear = Year.isLeap(birthYear) ? 366 : 365;
        int randomDayOfYear = random.nextInt(1, daysInYear + 1);

        return LocalDate.ofYearDay(birthYear, randomDayOfYear);
    }

    /**
     * Gera uma data de nascimento automaticamente determinando se o profissional
     * deve ter educação formal baseado na probabilidade do papel.
     *
     * @param role A função do membro da equipe.
     * @return Uma {@link LocalDate} representando a data de nascimento gerada.
     */
    public LocalDate generateRealisticBirthDate(CrewRoleEnum role) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        boolean shouldHaveEducation = shouldHaveEducation(role, random);
        return generateEducationAwareBirthDate(role, shouldHaveEducation);
    }

    /**
     * Verifica se a idade gerada corresponde a um adulto (18 anos ou mais).
     *
     * @param age A idade a ser verificada.
     * @return {@code true} se a idade for 18 ou mais, {@code false} caso contrário.
     */
    public boolean isAdultAge(int age) {
        return age >= 18;
    }

    /**
     * Verifica se a idade é adequada para um profissional com educação formal.
     *
     * @param role A função do membro da equipe.
     * @param age A idade a ser verificada.
     * @return {@code true} se a idade for adequada para educação formal, {@code false} caso contrário.
     */
    public boolean isEducationAppropriateAge(CrewRoleEnum role, int age) {
        return age >= getMinimumEducationAge(role);
    }

    /**
     * Determina se um profissional de determinado papel deveria ter educação formal
     * baseado em probabilidades realistas da indústria.
     *
     * @param role A função do membro da equipe.
     * @param random A instância de {@link ThreadLocalRandom}.
     * @return {@code true} se deveria ter educação formal, {@code false} caso contrário.
     */
    private boolean shouldHaveEducation(CrewRoleEnum role, ThreadLocalRandom random) {
        double educationProbability = switch (role) {
            case DIRECTOR, CINEMATOGRAPHER, VFX_ARTIST, SOUND_DESIGNER -> 0.85; // Alta probabilidade
            case ART_DIRECTOR, EDITOR, VISUAL_EFFECTS_SUPERVISOR -> 0.80;
            case COMPOSER -> 0.75;
            case MAKEUP_ARTIST, COSTUME_DESIGNER -> 0.60;
            case SCREENWRITER -> 0.70;
            case ACTOR -> 0.40; // Muitos atores não têm educação formal
            case STUNT_PERFORMER -> 0.30; // Mais baseado em habilidades físicas
            default -> EDUCATED_PROFESSIONAL_PROBABILITY;
        };

        return random.nextDouble() < educationProbability;
    }

    /**
     * Obtém parâmetros de distribuição de idade considerando educação formal.
     *
     * @param role A função do membro da equipe.
     * @param hasEducation Se possui educação formal.
     * @param random A instância de {@link ThreadLocalRandom}.
     * @return Um record {@link AgeDistributionParameters} com os parâmetros ajustados.
     */
    private AgeDistributionParameters getEducationAwareAgeDistribution(CrewRoleEnum role, boolean hasEducation, ThreadLocalRandom random) {
        if (role == CrewRoleEnum.ACTOR) {
            if (random.nextDouble() < CHILD_ACTOR_PROBABILITY) {
                return new AgeDistributionParameters(5, 17, 1.5, 1.5);
            } else if (hasEducation) {
                // Atores com educação formal tendem a começar um pouco mais tarde
                return new AgeDistributionParameters(21, 90, 2.0, 3.0);
            } else {
                return new AgeDistributionParameters(18, 90, 2.0, 3.0);
            }
        }

        // Para outros papéis, ajusta a idade mínima baseado na educação
        AgeDistributionParameters baseParams = getAgeDistributionParametersForRole(role, random);

        if (hasEducation) {
            int educationMinAge = getMinimumEducationAge(role);
            int adjustedMinAge = Math.max(baseParams.minAge(), educationMinAge);

            // Ajusta alpha/beta para refletir que profissionais educados tendem a ser um pouco mais velhos
            double adjustedAlpha = baseParams.alpha() + 0.5;

            return new AgeDistributionParameters(
                    adjustedMinAge,
                    baseParams.maxAge(),
                    adjustedAlpha,
                    baseParams.beta()
            );
        }

        return baseParams;
    }

    /**
     * Gera uma amostra aleatória de uma distribuição Beta.
     *
     * @param alpha  O primeiro parâmetro de forma (α).
     * @param beta   O segundo parâmetro de forma (β).
     * @param random A instância de {@link ThreadLocalRandom}.
     * @return um double entre 0.0 e 1.0.
     */
    private double sampleBeta(double alpha, double beta, ThreadLocalRandom random) {
        double gammaSample1 = Math.pow(random.nextDouble(), 1.0 / alpha);
        double gammaSample2 = Math.pow(random.nextDouble(), 1.0 / beta);

        return gammaSample1 / (gammaSample1 + gammaSample2);
    }

    /**
     * Centraliza a lógica para definir os parâmetros de distribuição de idade para cada função.
     *
     * @param role   A função do membro da equipe.
     * @param random A instância de {@link ThreadLocalRandom} para decisões estocásticas.
     * @return Um record {@link AgeDistributionParameters} contendo os parâmetros para a geração da idade.
     */
    private AgeDistributionParameters getAgeDistributionParametersForRole(CrewRoleEnum role, ThreadLocalRandom random) {
        return switch (role) {
            case ACTOR -> {
                if (random.nextDouble() < CHILD_ACTOR_PROBABILITY) {
                    yield new AgeDistributionParameters(5, 17, 1.5, 1.5);
                } else {
                    yield new AgeDistributionParameters(18, 90, 2.0, 3.0);
                }
            }
            case DIRECTOR, SCREENWRITER ->
                    new AgeDistributionParameters(25, 85, 3.0, 2.0);

            case STUNT_PERFORMER ->
                    new AgeDistributionParameters(18, 55, 2.0, 4.0);

            case CINEMATOGRAPHER, ART_DIRECTOR, EDITOR ->
                    new AgeDistributionParameters(22, 75, 2.5, 2.5);

            case VFX_ARTIST, SOUND_DESIGNER, VISUAL_EFFECTS_SUPERVISOR ->
                    new AgeDistributionParameters(20, 70, 2.0, 2.0);

            case COMPOSER ->
                    new AgeDistributionParameters(18, 90, 2.0, 2.5);

            case MAKEUP_ARTIST, COSTUME_DESIGNER ->
                    new AgeDistributionParameters(18, 75, 2.0, 2.0);

            default ->
                    new AgeDistributionParameters(18, 80, 2.0, 2.0);
        };
    }

    /**
     * Valida se uma data de nascimento é aceitável para um determinado papel.
     *
     * @param role      O papel do membro da equipe.
     * @param birthDate A data de nascimento a ser validada.
     * @return {@code true} se a data de nascimento resultar em uma idade válida, {@code false} caso contrário.
     */
    public boolean validateBirthDate(CrewRoleEnum role, LocalDate birthDate) {
        if (birthDate == null) {
            return false;
        }
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        return validateAgeForRole(role, age);
    }

    /**
     * Valida se uma data de nascimento é apropriada para um profissional com educação formal.
     *
     * @param role      O papel do membro da equipe.
     * @param birthDate A data de nascimento a ser validada.
     * @param hasEducation Se o profissional possui educação formal.
     * @return {@code true} se a data de nascimento for válida considerando educação, {@code false} caso contrário.
     */
    public boolean validateEducationAwareBirthDate(CrewRoleEnum role, LocalDate birthDate, boolean hasEducation) {
        if (!validateBirthDate(role, birthDate)) {
            return false;
        }

        if (hasEducation) {
            int age = Period.between(birthDate, LocalDate.now()).getYears();
            return isEducationAppropriateAge(role, age);
        }

        return true;
    }

    /**
     * Valida se uma idade está dentro do intervalo permitido para um papel.
     *
     * @param role O papel do membro da equipe.
     * @param age  A idade a ser validada.
     * @return {@code true} se a idade for válida, {@code false} caso contrário.
     */
    public boolean validateAgeForRole(CrewRoleEnum role, int age) {
        int minAge = getMinimumAgeForRole(role);
        int maxAge = getMaximumAgeForRole(role);
        return age >= minAge && age <= maxAge;
    }

    private int getMinimumAgeForRole(CrewRoleEnum role) {
        return switch (role) {
            case ACTOR -> 5;
            case DIRECTOR, SCREENWRITER -> 25;
            case STUNT_PERFORMER, COMPOSER, MAKEUP_ARTIST, COSTUME_DESIGNER -> 18;
            case CINEMATOGRAPHER, ART_DIRECTOR, EDITOR -> 22;
            case VFX_ARTIST, SOUND_DESIGNER, VISUAL_EFFECTS_SUPERVISOR -> 20;
            default -> 18;
        };
    }

    private int getMaximumAgeForRole(CrewRoleEnum role) {
        return switch (role) {
            case ACTOR, COMPOSER -> 90;
            case DIRECTOR, SCREENWRITER -> 85;
            case STUNT_PERFORMER -> 55;
            case CINEMATOGRAPHER, ART_DIRECTOR, EDITOR, MAKEUP_ARTIST, COSTUME_DESIGNER -> 75;
            case VFX_ARTIST, SOUND_DESIGNER, VISUAL_EFFECTS_SUPERVISOR -> 70;
            default -> 80;
        };
    }

    private int getMinimumEducationAge(CrewRoleEnum role) {
        return switch (role) {
            case DIRECTOR, CINEMATOGRAPHER -> 22; // Graduação + experiência inicial
            case COMPOSER -> 21; // Conservatório ou graduação em música
            case VFX_ARTIST, SOUND_DESIGNER -> 20; // Curso técnico ou graduação
            case ACTOR -> 7; // Pode começar muito jovem, mas educação formal seria mais tarde
            case MAKEUP_ARTIST, COSTUME_DESIGNER -> 18; // Cursos técnicos
            case STUNT_PERFORMER -> 19; // Treinamento especializado
            case ART_DIRECTOR, EDITOR -> 22; // Graduação típica
            case SCREENWRITER -> 21; // Educação em escrita/cinema
            case VISUAL_EFFECTS_SUPERVISOR -> 24; // Graduação + experiência
            default -> 18;
        };
    }

    /**
     * Record interno para encapsular os parâmetros de distribuição de idade.
     */
    private record AgeDistributionParameters(int minAge, int maxAge, double alpha, double beta) {}
}