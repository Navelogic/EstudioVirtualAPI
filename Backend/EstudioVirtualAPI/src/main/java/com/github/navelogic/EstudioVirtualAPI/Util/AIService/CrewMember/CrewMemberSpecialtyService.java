package com.github.navelogic.estudiovirtualapi.Util.AIService.CrewMember;

import com.github.navelogic.estudiovirtualapi.Util.Enum.GenreEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Serviço responsável por gerar especialidades para membros da equipe (CrewMember).
 */
@Service
@RequiredArgsConstructor
public class CrewMemberSpecialtyService {

    /**
     * Gera uma especialidade aleatoria.
     */
    public GenreEnum generateSpecialty() {
        return GenreEnum.values()[ThreadLocalRandom.current().nextInt(GenreEnum.values().length)];
    }
}
