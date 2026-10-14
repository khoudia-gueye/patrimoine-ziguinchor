package com.ziguinchor.patrimoine.service;

import com.ziguinchor.patrimoine.model.Avis;

public interface AvisService {
    Avis enregistrer(Avis avis);
    void supprimer(Long id);
}
