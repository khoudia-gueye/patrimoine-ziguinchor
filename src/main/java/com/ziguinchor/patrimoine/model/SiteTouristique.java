package com.ziguinchor.patrimoine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "site_touristique")
public class SiteTouristique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du site est obligatoire")
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "La description est obligatoire")
    @Column(length = 2000)
    private String description;

    private String adresse;
    private String quartier;
    private String imageUrl;
    private Double latitude;
    private Double longitude;
    private LocalDate dateAjout;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "siteTouristique", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avis> avis = new ArrayList<>();

    public SiteTouristique() {}

    @PrePersist
    public void prePersist() {
        if (dateAjout == null) dateAjout = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getQuartier() { return quartier; }
    public void setQuartier(String quartier) { this.quartier = quartier; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public LocalDate getDateAjout() { return dateAjout; }
    public void setDateAjout(LocalDate dateAjout) { this.dateAjout = dateAjout; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
    public List<Avis> getAvis() { return avis; }
    public void setAvis(List<Avis> avis) { this.avis = avis; }

    public double getNoteMoyenne() {
        if (avis == null || avis.isEmpty()) return 0;
        return avis.stream().mapToInt(Avis::getNote).average().orElse(0);
    }
}
