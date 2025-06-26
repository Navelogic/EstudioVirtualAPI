package com.github.navelogic.estudiovirtualapi.Util.AIService.CrewMember;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Serviço dedicado à geração de popularidade inicial para membros da equipe (CrewMember).
 */
@Service
@NoArgsConstructor
public class CrewMemberPopularityService {

    /**
     * Gera um valor de popularidade inicial para um membro da equipe.
     */
    public int generateInitialPopularity() {
        double chance = Math.random();
        if (chance < 0.7) {
            return 1 + (int) (Math.random() * 5);
        } else {
            return 6 + (int) (Math.random() * 5);
        }
    }
}
