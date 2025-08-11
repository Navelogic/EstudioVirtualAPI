package com.github.navelogic.estudiovirtualapi.Util.NameAI.Service;

import com.github.navelogic.estudiovirtualapi.Util.AIService.NameGeneratorService;
import com.github.navelogic.estudiovirtualapi.Util.DTO.NameAI.StudioNameAICreateDTO;
import com.github.navelogic.estudiovirtualapi.Util.Enum.StudioNameTypeAIEnum;
import com.github.navelogic.estudiovirtualapi.Util.NameAI.Model.StudioNameAI;
import com.github.navelogic.estudiovirtualapi.Util.NameAI.Repository.StudioNameAIRepository;
import jakarta.validation.Valid;
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

    public StudioNameAI createStudioNameAI(StudioNameAICreateDTO dto) {
        var newName = new StudioNameAI();
        newName.setName(dto.getName());
        newName.setStudioNameTypeAIEnum(dto.getStudioNameTypeAIEnum());

        return studioNameAIRepository.save(newName);
    }

    public String generateStudioName(){
        List<StudioNameAI> prefixList = studioNameAIRepository.findAllByStudioNameTypeAIEnum(StudioNameTypeAIEnum.PREFIX);
        List<StudioNameAI> sufixList = studioNameAIRepository.findAllByStudioNameTypeAIEnum(StudioNameTypeAIEnum.SUFFIX);

        if (prefixList.isEmpty() || sufixList.isEmpty()) {
            return nameGeneratorService.generateStudioName();
        }

        String prefix = prefixList.get(random.nextInt(prefixList.size())).getName();
        String suffix = sufixList.get(random.nextInt(sufixList.size())).getName();
        return prefix + " " + suffix;
    }

}
