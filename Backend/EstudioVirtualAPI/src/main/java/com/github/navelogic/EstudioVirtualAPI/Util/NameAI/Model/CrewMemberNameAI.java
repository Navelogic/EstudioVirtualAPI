package com.github.navelogic.estudiovirtualapi.Util.NameAI.Model;

import com.github.navelogic.estudiovirtualapi.Util.Enum.CrewMemberNameTypeAIEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.GenderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrewMemberNameAI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CrewMemberNameTypeAIEnum crewMemberNameTypeAIEnum;

    @Enumerated(EnumType.STRING)
    private GenderEnum genderEnum;

}
