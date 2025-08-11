package com.github.navelogic.estudiovirtualapi.Util.AIService.Studio;


import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Enum.FinancialEntityTypeEnum;
import com.github.navelogic.estudiovirtualapi.Engines.MoneyEngine.Model.Account;
import com.github.navelogic.estudiovirtualapi.Model.Studio;
import com.github.navelogic.estudiovirtualapi.Repository.StudioRepository;
import com.github.navelogic.estudiovirtualapi.Util.NameAI.Service.StudioNameAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudioCreateAIService {

    private final StudioRepository studioRepository;
    private final StudioNameAIService studioNameAIService;

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void scheduleCreateAiStudios() {
        String studioName = studioNameAIService.generateStudioName();
        Studio studio = new Studio();
        Account finance = new Account();
        studio.setName(studioName);
        studio.setIsAiControlled(true);
        studio.setAccount(finance);
        finance.setStudioOwner(studio);
        finance.setHolderType(FinancialEntityTypeEnum.STUDIO);
        studioRepository.save(studio);
    }
}
