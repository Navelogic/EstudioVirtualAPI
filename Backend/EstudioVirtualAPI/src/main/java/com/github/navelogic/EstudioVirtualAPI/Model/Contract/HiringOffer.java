package com.github.navelogic.estudiovirtualapi.Model.Contract;

import com.github.navelogic.estudiovirtualapi.Model.CrewMember;
import com.github.navelogic.estudiovirtualapi.Model.Production.Production;
import com.github.navelogic.estudiovirtualapi.Model.Studio;
import com.github.navelogic.estudiovirtualapi.Util.Audit.Auditable;
import com.github.navelogic.estudiovirtualapi.Util.Enum.ContractTypeEnum;
import com.github.navelogic.estudiovirtualapi.Util.Enum.OfferStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class HiringOffer extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id")
    private Studio offeringStudio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew_member_id")
    private CrewMember targetCrewMember;

    @Enumerated(EnumType.STRING)
    private OfferStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_id", nullable = true)
    private Production production;

    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive = true;

    private BigDecimal baseSalary;
    private BigDecimal signingBonus;
    private BigDecimal royaltiesPercentage;
    private BigDecimal minimumGuarantee;
    private BigDecimal terminationPenalty;

    @Enumerated(EnumType.STRING)
    private ContractTypeEnum contractType;

    private Integer minimumProductionsRequired;
    private Boolean requiresExclusivity;
    private Boolean includesImageRights;
    private Boolean includesCreditsRequirement;

    private Boolean includesAccommodation;
    private Boolean includesTransportation;
    private Boolean includesInsurance;
    private Boolean includesPerformanceBonus;
    private Boolean requiresStuntDouble;
    private Boolean includesCharacterMerchandising;
    private Boolean requiresWardrobe;

    @Column(columnDefinition = "TEXT")
    private String counterOfferDemands;
}
