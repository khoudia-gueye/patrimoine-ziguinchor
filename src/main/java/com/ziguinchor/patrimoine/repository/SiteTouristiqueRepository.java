package com.ziguinchor.patrimoine.repository;

import com.ziguinchor.patrimoine.model.SiteTouristique;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SiteTouristiqueRepository extends JpaRepository<SiteTouristique, Long> {
    List<SiteTouristique> findByNomContainingIgnoreCase(String motCle);
    List<SiteTouristique> findByCategorieId(Long categorieId);
    List<SiteTouristique> findByQuartierContainingIgnoreCase(String quartier);
    Optional<SiteTouristique> findByNom(String nom);
}
