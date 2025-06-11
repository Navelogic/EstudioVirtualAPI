package com.github.navelogic.estudiovirtualapi.Util.Service.Studio;


import com.github.navelogic.estudiovirtualapi.Model.Finance.StudioFinance;
import com.github.navelogic.estudiovirtualapi.Model.Studio;
import com.github.navelogic.estudiovirtualapi.Repository.StudioRepository;
import com.github.navelogic.estudiovirtualapi.Util.Service.NameGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class StudioCreateAIService {

    private final StudioRepository studioRepository;
    private final NameGeneratorService nameGeneratorService;
    private final Random random = new Random();

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void scheduleCreateAiStudios() {
        String studioName = nameGeneratorService.generateStudioName();
        Studio studio = new Studio();
        StudioFinance finance = new StudioFinance();
        studio.setName(studioName);
        studio.setIsAiControlled(true);
        studio.setFinance(finance);
        finance.setStudio(studio);
        studioRepository.save(studio);
    }
}
