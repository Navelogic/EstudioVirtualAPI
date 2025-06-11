package com.github.navelogic.estudiovirtualapi.Model.Production.Serie;

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
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer episodeNumber;
    private Integer durationMinutes;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;
}
