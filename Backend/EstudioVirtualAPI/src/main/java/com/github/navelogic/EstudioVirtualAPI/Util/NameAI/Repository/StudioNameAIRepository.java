package com.github.navelogic.estudiovirtualapi.Util.NameAI.Repository;

import com.github.navelogic.estudiovirtualapi.Util.Enum.StudioNameTypeAIEnum;
import com.github.navelogic.estudiovirtualapi.Util.NameAI.Model.StudioNameAI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudioNameAIRepository extends JpaRepository<StudioNameAI, Long> {
    List<StudioNameAI> findAllByStudioNameTypeAI(StudioNameTypeAIEnum StudioNameTypeAIEnum);
}
