package com.ziguinchor.patrimoine.repository;

import com.ziguinchor.patrimoine.model.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AvisRepository extends JpaRepository<Avis, Long> {
    List<Avis> findBySiteTouristiqueId(Long siteId);
}
