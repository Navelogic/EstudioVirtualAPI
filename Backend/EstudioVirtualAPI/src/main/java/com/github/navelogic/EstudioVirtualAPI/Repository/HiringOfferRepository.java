package com.github.navelogic.estudiovirtualapi.Repository;

import com.github.navelogic.estudiovirtualapi.Model.Contract.HiringOffer;
import com.github.navelogic.estudiovirtualapi.Util.Enum.OfferStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HiringOfferRepository extends JpaRepository<HiringOffer, Long> {
    List<HiringOffer> findByStatus(OfferStatusEnum offerStatusEnum);
}
