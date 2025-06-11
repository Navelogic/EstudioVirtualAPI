package com.github.navelogic.estudiovirtualapi.Model.Production.Serie;


import com.github.navelogic.estudiovirtualapi.Model.Production.Production;
import com.github.navelogic.estudiovirtualapi.Util.Enum.BroadcastTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Serie extends Production {

    @Enumerated(EnumType.STRING)
    private BroadcastTypeEnum broadcastType;

    private String networkOrPlatform;
    private Boolean isOnGoing = true;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Season> seasons = new ArrayList<>();
}
