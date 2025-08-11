package com.github.navelogic.estudiovirtualapi.Model.Production;

import com.github.navelogic.estudiovirtualapi.Util.Enum.DistributionModelEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie extends Production {

    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    private DistributionModelEnum distributionModel;

    private Boolean isCult = false;
    private Boolean isViral = false;
}
