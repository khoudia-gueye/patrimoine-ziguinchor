package com.ziguinchor.patrimoine.service.impl;

import com.ziguinchor.patrimoine.model.Categorie;
import com.ziguinchor.patrimoine.repository.CategorieRepository;
import com.ziguinchor.patrimoine.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    @Autowired
    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    public List<Categorie> listerToutes() { return categorieRepository.findAll(); }

    @Override
    public Categorie trouverParId(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Catégorie introuvable avec l'id : " + id));
    }

    @Override
    public Categorie enregistrer(Categorie categorie) { return categorieRepository.save(categorie); }

    @Override
    public void supprimer(Long id) { categorieRepository.deleteById(id); }
}
