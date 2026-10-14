package com.ziguinchor.patrimoine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "visite")
public class Visite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "site_touristique_id")
    @NotNull(message = "Le site est obligatoire")
    private SiteTouristique site;

    @ManyToOne
    @JoinColumn(name = "circuit_id")
    private Circuit circuit;

    @Enumerated(EnumType.STRING)
    private StatutVisite statut = StatutVisite.PLANIFIEE;

    // Simple nom du guide qui encadre la visite (pas un rôle système séparé)
    private String guide;

    @NotNull(message = "La date et l'heure sont obligatoires")
    @Future(message = "La visite doit être planifiée dans le futur")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateHeureDebut;

    @Min(value = 15, message = "La durée minimale est de 15 minutes")
    private int dureeMinutes = 60;

    @Min(value = 1, message = "Il faut au moins 1 place")
    private int placesMax = 10;

    private int placesReservees = 0;

    @OneToMany(mappedBy = "visite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    public Visite() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public SiteTouristique getSite() { return site; }
    public void setSite(SiteTouristique site) { this.site = site; }
    public Circuit getCircuit() { return circuit; }
    public void setCircuit(Circuit circuit) { this.circuit = circuit; }
    public StatutVisite getStatut() { return statut; }
    public void setStatut(StatutVisite statut) { this.statut = statut; }
    public String getGuide() { return guide; }
    public void setGuide(String guide) { this.guide = guide; }
    public LocalDateTime getDateHeureDebut() { return dateHeureDebut; }
    public void setDateHeureDebut(LocalDateTime dateHeureDebut) { this.dateHeureDebut = dateHeureDebut; }
    public int getDureeMinutes() { return dureeMinutes; }
    public void setDureeMinutes(int dureeMinutes) { this.dureeMinutes = dureeMinutes; }
    public int getPlacesMax() { return placesMax; }
    public void setPlacesMax(int placesMax) { this.placesMax = placesMax; }
    public int getPlacesReservees() { return placesReservees; }
    public void setPlacesReservees(int placesReservees) { this.placesReservees = placesReservees; }
    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    public int getPlacesDisponibles() { return placesMax - placesReservees; }
    public boolean isComplete() { return getPlacesDisponibles() <= 0; }
}
