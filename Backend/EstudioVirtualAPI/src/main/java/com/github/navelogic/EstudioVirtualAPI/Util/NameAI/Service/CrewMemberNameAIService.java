package com.github.navelogic.estudiovirtualapi.Util.NameAI.Service;

import com.github.navelogic.estudiovirtualapi.Util.AIService.NameGeneratorService;
import com.github.navelogic.estudiovirtualapi.Util.DTO.NameAI.CrewMemberNameAICreateDTO;
import com.github.navelogic.estudiovirtualapi.Util.Enum.CrewMemberNameTypeAIEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.GenderEnum;
import com.github.navelogic.estudiovirtualapi.Util.NameAI.Model.CrewMemberNameAI;
import com.github.navelogic.estudiovirtualapi.Util.NameAI.Repository.CrewMemberNameAIRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CrewMemberNameAIService {

    private final CrewMemberNameAIRepository crewMemberNameAIRepository;
    private final NameGeneratorService nameGeneratorService;
    private static final Random random = new Random();

    public CrewMemberNameAI createCrewMemberNameAI(CrewMemberNameAICreateDTO dto) {
        var newName = new CrewMemberNameAI();
        newName.setName(dto.getName());
        newName.setCrewMemberNameTypeAIEnum(dto.getCrewMemberNameTypeAIEnum());
        newName.setGenderEnum(dto.getGenderEnum());

        return crewMemberNameAIRepository.save(newName);
    }

    public String generateCrewMemberName(String gender) {
        List<CrewMemberNameAI> firstNameList = crewMemberNameAIRepository
                .findAllByCrewMemberNameTypeAIEnum(CrewMemberNameTypeAIEnum.FIRST_NAME);
        List<CrewMemberNameAI> lastNameList = crewMemberNameAIRepository
                .findAllByCrewMemberNameTypeAIEnum(CrewMemberNameTypeAIEnum.LAST_NAME);

        if (firstNameList.isEmpty() || lastNameList.isEmpty()) {
            return nameGeneratorService.generateCrewName(gender);
        }

        GenderEnum genderEnum;
        try {
            genderEnum = GenderEnum.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Gênero inválido" + gender);
        }

        List<CrewMemberNameAI> filteredFirstNames = firstNameList.stream()
                .filter(name -> name.getGenderEnum() == genderEnum)
                .toList();

        if (filteredFirstNames.isEmpty()) {
            return nameGeneratorService.generateCrewName(gender);
        }

        String firstName = filteredFirstNames.get(random.nextInt(filteredFirstNames.size())).getName();
        String lastName = lastNameList.get(random.nextInt(lastNameList.size())).getName();

        return firstName + " " + lastName;
    }

}
