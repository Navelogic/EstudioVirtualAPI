package com.github.navelogic.estudiovirtualapi.Util.NameAI.Model;

import com.github.navelogic.estudiovirtualapi.Util.Enum.StudioNameTypeAIEnum;
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
public class StudioNameAI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private StudioNameTypeAIEnum studioNameTypeAIEnum;
}
