package com.github.navelogic.estudiovirtualapi.Util.NameAI.Repository;

import com.github.navelogic.estudiovirtualapi.Util.Enum.CrewMemberNameTypeAIEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.GenderEnum;
import com.github.navelogic.estudiovirtualapi.Util.NameAI.Model.CrewMemberNameAI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrewMemberNameAIRepository extends JpaRepository<CrewMemberNameAI, Long> {
    List<CrewMemberNameAI> findAllByCrewMemberNameTypeAIEnum(CrewMemberNameTypeAIEnum crewMemberNameTypeAIEnum);
    List<CrewMemberNameAI> findAllByCrewMemberNameTypeAIEnumAndGenderEnum(CrewMemberNameTypeAIEnum type, GenderEnum gender);
}
