package com.github.navelogic.estudiovirtualapi.Util.AIService.CrewMember;

import com.github.navelogic.estudiovirtualapi.Util.Enum.GenderEnum;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Serviço dedicado à geração de gênero para membros da equipe (CrewMember).
 */
@Service
@NoArgsConstructor
public class CrewMemberGenderService {


    private static final double MALE_PROBABILITY_ADULT = 0.49;
    private static final double FEMALE_PROBABILITY_ADULT = 0.49;
    private static final double MALE_PROBABILITY_CHILD = 0.5;

    /**
     * Gera um gênero com base na faixa etária (adulto ou não).
     * <p>
     * Para não-adultos, a simulação usa uma divisão binária (Masculino/Feminino) 50/50,
     * simplificando o modelo para idades mais jovens.
     * <p>
     * Para adultos, a distribuição é ajustada para ser mais próxima de dados demográficos,
     * incluindo uma pequena probabilidade para identidades não-binárias.
     *
     * @param isAdult Um booleano que indica se o membro da equipe é adulto.
     * @return Um {@link GenderEnum}
     */
    public GenderEnum generateGender(boolean isAdult) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        if (!isAdult) {
            return random.nextDouble() < MALE_PROBABILITY_CHILD ? GenderEnum.MALE : GenderEnum.FEMALE;
        } else {
            double roll = random.nextDouble();

            if (roll < MALE_PROBABILITY_ADULT) {
                return GenderEnum.MALE;
            } else if (roll < MALE_PROBABILITY_ADULT + FEMALE_PROBABILITY_ADULT) {
                return GenderEnum.FEMALE;
            } else {
                return GenderEnum.NON_BINARY;
            }
        }
    }
}
