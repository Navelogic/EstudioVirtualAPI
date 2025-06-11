package com.github.navelogic.estudiovirtualapi.Model;

import com.github.navelogic.estudiovirtualapi.Model.Finance.StudioFinance;
import com.github.navelogic.estudiovirtualapi.Model.Production.Production;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player owner;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Production> productions = new HashSet<>();

    @OneToOne(mappedBy = "studio", cascade = CascadeType.ALL, orphanRemoval = true)
    private StudioFinance finance;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    private Boolean isActive = true;
    private Boolean isAiControlled = false;

}
