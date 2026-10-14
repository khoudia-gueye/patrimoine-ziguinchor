package com.ziguinchor.patrimoine.repository;

import com.ziguinchor.patrimoine.model.Circuit;
import com.ziguinchor.patrimoine.model.StatutCircuit;
import com.ziguinchor.patrimoine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CircuitRepository extends JpaRepository<Circuit, Long> {
    List<Circuit> findByStatut(StatutCircuit statut);
    List<Circuit> findByOperateur(User operateur);
}
