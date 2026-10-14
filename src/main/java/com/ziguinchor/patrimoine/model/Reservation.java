package com.ziguinchor.patrimoine.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "touriste_id")
    private User touriste;

    @ManyToOne
    @JoinColumn(name = "visite_id")
    private Visite visite;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut = StatutReservation.CONFIRME;

    @Column(unique = true)
    private String qrCodeHash;

    private Double montant = 0.0;

    private LocalDateTime dateReservation;

    public Reservation() {}

    @PrePersist
    public void prePersist() {
        if (dateReservation == null) dateReservation = LocalDateTime.now();
        if (qrCodeHash == null) qrCodeHash = UUID.randomUUID().toString();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getTouriste() { return touriste; }
    public void setTouriste(User touriste) { this.touriste = touriste; }
    public Visite getVisite() { return visite; }
    public void setVisite(Visite visite) { this.visite = visite; }
    public StatutReservation getStatut() { return statut; }
    public void setStatut(StatutReservation statut) { this.statut = statut; }
    public String getQrCodeHash() { return qrCodeHash; }
    public void setQrCodeHash(String qrCodeHash) { this.qrCodeHash = qrCodeHash; }
    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }
    public LocalDateTime getDateReservation() { return dateReservation; }
    public void setDateReservation(LocalDateTime dateReservation) { this.dateReservation = dateReservation; }
}
