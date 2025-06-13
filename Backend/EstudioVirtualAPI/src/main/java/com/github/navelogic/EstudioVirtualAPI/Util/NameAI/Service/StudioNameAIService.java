package com.github.navelogic.estudiovirtualapi.Util.NameAI.Service;

import com.github.navelogic.estudiovirtualapi.Util.AIService.NameGeneratorService;
import com.github.navelogic.estudiovirtualapi.Util.Enum.StudioNameTypeAIEnum;
import com.github.navelogic.estudiovirtualapi.Util.NameAI.Model.StudioNameAI;
import com.github.navelogic.estudiovirtualapi.Util.NameAI.Repository.StudioNameAIRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class StudioNameAIService {

    private final StudioNameAIRepository studioNameAIRepository;
    private final NameGeneratorService nameGeneratorService;
    private static final Random random = new Random();

    public StudioNameAI createStudioNameAI(StudioNameAI studioNameAI) {
        return studioNameAIRepository.save(studioNameAI);
    }

    public String generateStudioName(){
        List<StudioNameAI> prefixList = studioNameAIRepository.findAllByStudioNameTypeAI(StudioNameTypeAIEnum.PREFIX);
        List<StudioNameAI> sufixList = studioNameAIRepository.findAllByStudioNameTypeAI(StudioNameTypeAIEnum.SUFFIX);

        if (prefixList.isEmpty() || sufixList.isEmpty()) {
            return nameGeneratorService.generateStudioName();
        }

        String prefix = prefixList.get(random.nextInt(prefixList.size())).getName();
        String suffix = sufixList.get(random.nextInt(sufixList.size())).getName();
        return prefix + " " + suffix;
    }

}
