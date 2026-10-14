package com.ziguinchor.patrimoine.controller;

import com.ziguinchor.patrimoine.model.Reservation;
import com.ziguinchor.patrimoine.model.StatutReservation;
import com.ziguinchor.patrimoine.model.User;
import com.ziguinchor.patrimoine.model.Visite;
import com.ziguinchor.patrimoine.repository.ReservationRepository;
import com.ziguinchor.patrimoine.repository.UserRepository;
import com.ziguinchor.patrimoine.repository.VisiteRepository;
import com.ziguinchor.patrimoine.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final VisiteRepository visiteRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository,
                                  VisiteRepository visiteRepository,
                                  UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.visiteRepository = visiteRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String mesReservations(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        User touriste = principal.getUser();
        model.addAttribute("reservations", reservationRepository.findByTouristeOrderByDateReservationDesc(touriste));
        return "reservations/liste";
    }

    @PostMapping("/reserver/{visiteId}")
    public String reserver(@PathVariable Long visiteId,
                            @AuthenticationPrincipal CustomUserDetails principal,
                            RedirectAttributes redirectAttributes) {

        Visite visite = visiteRepository.findById(visiteId)
                .orElseThrow(() -> new IllegalArgumentException("Visite introuvable"));

        if (visite.isComplete()) {
            redirectAttributes.addFlashAttribute("messagePatience",
                    "Cette visite est complète. Merci de patienter : une place pourrait se libérer en cas d'annulation, ou choisis une autre date.");
            return "redirect:/visites";
        }

        User touriste = userRepository.findById(principal.getUser().getId()).orElseThrow();

        Reservation reservation = new Reservation();
        reservation.setTouriste(touriste);
        reservation.setVisite(visite);
        reservation.setStatut(StatutReservation.CONFIRME);
        reservation.setMontant(visite.getCircuit() != null && visite.getCircuit().getPrix() != null
                ? visite.getCircuit().getPrix() : 0.0);
        reservationRepository.save(reservation);

        visite.setPlacesReservees(visite.getPlacesReservees() + 1);
        visiteRepository.save(visite);

        redirectAttributes.addFlashAttribute("messageSucces", "Réservation confirmée !");
        return "redirect:/reservations";
    }

    @PostMapping("/annuler/{id}")
    public String annuler(@PathVariable Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable"));

        if (reservation.getStatut() == StatutReservation.CONFIRME) {
            reservation.setStatut(StatutReservation.ANNULE);
            reservationRepository.save(reservation);

            Visite visite = reservation.getVisite();
            visite.setPlacesReservees(Math.max(0, visite.getPlacesReservees() - 1));
            visiteRepository.save(visite);
        }

        return "redirect:/reservations";
    }
}
