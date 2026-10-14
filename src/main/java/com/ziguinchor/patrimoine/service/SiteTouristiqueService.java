package com.ziguinchor.patrimoine.service;

import com.ziguinchor.patrimoine.model.SiteTouristique;
import java.util.List;

public interface SiteTouristiqueService {
    List<SiteTouristique> listerTous();
    SiteTouristique trouverParId(Long id);
    SiteTouristique enregistrer(SiteTouristique site);
    void supprimer(Long id);
    List<SiteTouristique> rechercherParNom(String motCle);
    List<SiteTouristique> listerParCategorie(Long categorieId);
}
