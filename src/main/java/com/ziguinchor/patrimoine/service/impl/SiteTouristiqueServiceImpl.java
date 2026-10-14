package com.ziguinchor.patrimoine.service.impl;

import com.ziguinchor.patrimoine.model.SiteTouristique;
import com.ziguinchor.patrimoine.repository.SiteTouristiqueRepository;
import com.ziguinchor.patrimoine.service.SiteTouristiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SiteTouristiqueServiceImpl implements SiteTouristiqueService {

    private final SiteTouristiqueRepository siteTouristiqueRepository;

    @Autowired
    public SiteTouristiqueServiceImpl(SiteTouristiqueRepository siteTouristiqueRepository) {
        this.siteTouristiqueRepository = siteTouristiqueRepository;
    }

    @Override
    public List<SiteTouristique> listerTous() { return siteTouristiqueRepository.findAll(); }

    @Override
    public SiteTouristique trouverParId(Long id) {
        return siteTouristiqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Site touristique introuvable avec l'id : " + id));
    }

    @Override
    public SiteTouristique enregistrer(SiteTouristique site) { return siteTouristiqueRepository.save(site); }

    @Override
    public void supprimer(Long id) { siteTouristiqueRepository.deleteById(id); }

    @Override
    public List<SiteTouristique> rechercherParNom(String motCle) { return siteTouristiqueRepository.findByNomContainingIgnoreCase(motCle); }

    @Override
    public List<SiteTouristique> listerParCategorie(Long categorieId) { return siteTouristiqueRepository.findByCategorieId(categorieId); }
}
