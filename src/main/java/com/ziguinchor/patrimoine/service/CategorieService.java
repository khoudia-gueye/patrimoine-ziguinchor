package com.ziguinchor.patrimoine.service;

import com.ziguinchor.patrimoine.model.Categorie;
import java.util.List;

public interface CategorieService {
    List<Categorie> listerToutes();
    Categorie trouverParId(Long id);
    Categorie enregistrer(Categorie categorie);
    void supprimer(Long id);
}
