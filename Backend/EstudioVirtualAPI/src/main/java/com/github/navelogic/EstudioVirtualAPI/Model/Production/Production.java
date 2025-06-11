package com.github.navelogic.estudiovirtualapi.Model.Production;

import com.github.navelogic.estudiovirtualapi.Model.Finance.ProductionFinance;
import com.github.navelogic.estudiovirtualapi.Model.Studio;
import com.github.navelogic.estudiovirtualapi.Util.Enum.GenreEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.ProductionStatusEnum;
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
public class Production {

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

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @OneToOne(mappedBy = "production", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductionFinance finance;
}
