package com.ziguinchor.patrimoine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "circuit")
public class Circuit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du circuit est obligatoire")
    private String nom;

    @Column(length = 1000)
    private String description;

    @Min(value = 1, message = "La durée doit être d'au moins 1 jour")
    private int dureeJours = 1;

    @NotNull(message = "Le prix est obligatoire")
    private Double prix;

    @Enumerated(EnumType.STRING)
    private StatutCircuit statut = StatutCircuit.BROUILLON;

    @ManyToOne
    @JoinColumn(name = "operateur_id")
    private User operateur;

    @OneToMany(mappedBy = "circuit", cascade = CascadeType.ALL)
    private List<Visite> visites = new ArrayList<>();

    public Circuit() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getDureeJours() { return dureeJours; }
    public void setDureeJours(int dureeJours) { this.dureeJours = dureeJours; }
    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }
    public StatutCircuit getStatut() { return statut; }
    public void setStatut(StatutCircuit statut) { this.statut = statut; }
    public User getOperateur() { return operateur; }
    public void setOperateur(User operateur) { this.operateur = operateur; }
    public List<Visite> getVisites() { return visites; }
    public void setVisites(List<Visite> visites) { this.visites = visites; }
}
