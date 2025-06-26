package com.github.navelogic.estudiovirtualapi.Util.AIService.CrewMember;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Serviço dedicado à geração de produtividade para membros da equipe (CrewMember).
 */
@Service
@NoArgsConstructor
public class CrewMemberProductivityService {

    /**
     * Gera um nível de produtividade aleatório para um membro da equipe.
     * <p>
     * A produtividade é simulada como um valor entre 0 e 100.
     */
    public int generateProductivity() {
        return (int) (Math.random() * 101);
    }

}
