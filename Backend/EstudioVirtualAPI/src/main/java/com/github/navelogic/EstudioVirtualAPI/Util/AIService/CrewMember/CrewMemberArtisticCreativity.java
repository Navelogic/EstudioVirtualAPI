package com.github.navelogic.estudiovirtualapi.Util.AIService.CrewMember;

import com.github.navelogic.estudiovirtualapi.Util.Enum.CrewRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Serviço dedicado à geração do atributo 'Criatividade Artística' para um CrewMember.
 */
@Service
@RequiredArgsConstructor
public class CrewMemberArtisticCreativity {

    private static final Map<CrewRoleEnum, double[]> CREATIVITY_DISTRIBUTIONS;

    static {
        Map<CrewRoleEnum, double[]> distributions = new HashMap<>();

        // --- Funções Altamente Criativas ---
        distributions.put(CrewRoleEnum.DIRECTOR, new double[]{80, 10});
        distributions.put(CrewRoleEnum.SCREENWRITER, new double[]{82, 12});
        distributions.put(CrewRoleEnum.COMPOSER, new double[]{85, 15});
        distributions.put(CrewRoleEnum.ART_DIRECTOR, new double[]{78, 12});

        // --- Funções com Forte Componente Criativo/Interpretativo ---
        distributions.put(CrewRoleEnum.ACTOR, new double[]{70, 18});
        distributions.put(CrewRoleEnum.COSTUME_DESIGNER, new double[]{72, 15});
        distributions.put(CrewRoleEnum.MAKEUP_ARTIST, new double[]{68, 15});

        // --- Funções Técnicas com Elementos Criativos ---
        distributions.put(CrewRoleEnum.CINEMATOGRAPHER, new double[]{65, 15});
        distributions.put(CrewRoleEnum.EDITOR, new double[]{68, 12});
        distributions.put(CrewRoleEnum.VFX_ARTIST, new double[]{70, 15});
        distributions.put(CrewRoleEnum.VISUAL_EFFECTS_SUPERVISOR, new double[]{65, 12});
        distributions.put(CrewRoleEnum.SOUND_DESIGNER, new double[]{70, 15});

        // --- Funções com Menor Foco Criativo (mais físico/técnico) ---
        distributions.put(CrewRoleEnum.STUNT_PERFORMER, new double[]{40, 15});

        CREATIVITY_DISTRIBUTIONS = Collections.unmodifiableMap(distributions);
    }

    private static final double[] DEFAULT_DISTRIBUTION = {50, 15}; // Padrão para funções não mapeadas

    /**
     * Gera um valor de criatividade artística para uma determinada função.
     *
     * @param role A função do membro da equipe (ex: ATOR, DIRETOR).
     * @return Um inteiro entre 0 e 100 representando a criatividade artística.
     */
    public int generateArtisticCreativity(CrewRoleEnum role) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        double[] params = CREATIVITY_DISTRIBUTIONS.getOrDefault(role, DEFAULT_DISTRIBUTION);
        double mean = params[0];
        double stdDev = params[1];

        double gaussianSample = random.nextGaussian();

        double rawValue = mean + (gaussianSample * stdDev);

        return (int) Math.round(Math.max(0, Math.min(100, rawValue)));
    }

}
