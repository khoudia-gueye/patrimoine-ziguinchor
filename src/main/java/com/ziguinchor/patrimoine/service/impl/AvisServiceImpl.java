package com.ziguinchor.patrimoine.service.impl;

import com.ziguinchor.patrimoine.model.Avis;
import com.ziguinchor.patrimoine.repository.AvisRepository;
import com.ziguinchor.patrimoine.service.AvisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvisServiceImpl implements AvisService {

    private final AvisRepository avisRepository;

    @Autowired
    public AvisServiceImpl(AvisRepository avisRepository) {
        this.avisRepository = avisRepository;
    }

    @Override
    public Avis enregistrer(Avis avis) { return avisRepository.save(avis); }

    @Override
    public void supprimer(Long id) { avisRepository.deleteById(id); }
}
