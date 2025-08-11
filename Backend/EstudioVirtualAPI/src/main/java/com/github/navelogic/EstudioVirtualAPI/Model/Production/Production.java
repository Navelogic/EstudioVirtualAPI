package com.github.navelogic.estudiovirtualapi.Model.Production;

import com.github.navelogic.estudiovirtualapi.Model.Studio;
import com.github.navelogic.estudiovirtualapi.Util.Audit.Auditable;
import com.github.navelogic.estudiovirtualapi.Util.Enum.GenreEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.ProductionStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Production extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String tagline;

    @Enumerated(EnumType.STRING)
    private GenreEnum genre;

    @Enumerated(EnumType.STRING)
    private ProductionStatusEnum status;

    private Integer qualityScore = 0;
    private Integer criticScore = 0;
    private Integer audienceScore = 0;
    private Double hype = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id")
    private Studio studio;
}
