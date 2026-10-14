package com.ziguinchor.patrimoine.repository;

import com.ziguinchor.patrimoine.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
