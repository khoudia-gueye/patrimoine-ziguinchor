package com.ziguinchor.patrimoine.controller;

import com.ziguinchor.patrimoine.model.Circuit;
import com.ziguinchor.patrimoine.model.StatutCircuit;
import com.ziguinchor.patrimoine.model.Visite;
import com.ziguinchor.patrimoine.repository.CircuitRepository;
import com.ziguinchor.patrimoine.repository.SiteTouristiqueRepository;
import com.ziguinchor.patrimoine.repository.VisiteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/visites")
public class VisiteController {

    private final VisiteRepository visiteRepository;
    private final SiteTouristiqueRepository siteRepository;
    private final CircuitRepository circuitRepository;

    @Autowired
    public VisiteController(VisiteRepository visiteRepository,
                             SiteTouristiqueRepository siteRepository,
                             CircuitRepository circuitRepository) {
        this.visiteRepository = visiteRepository;
        this.siteRepository = siteRepository;
        this.circuitRepository = circuitRepository;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("visites", visiteRepository.findByDateHeureDebutAfterOrderByDateHeureDebutAsc(LocalDateTime.now()));
        return "visites/liste";
    }

    @GetMapping("/planifier")
    public String formulairePlanification(Model model) {
        model.addAttribute("visite", new Visite());
        model.addAttribute("sites", siteRepository.findAll());
        List<Circuit> circuits = circuitRepository.findByStatut(StatutCircuit.PUBLIE);
        model.addAttribute("circuits", circuits);
        return "visites/form";
    }

    @PostMapping("/enregistrer")
    public String enregistrer(@Valid @ModelAttribute("visite") Visite visite,
                               BindingResult resultat,
                               Model model) {
        if (resultat.hasErrors()) {
            model.addAttribute("sites", siteRepository.findAll());
            model.addAttribute("circuits", circuitRepository.findByStatut(StatutCircuit.PUBLIE));
            return "visites/form";
        }
        visiteRepository.save(visite);
        return "redirect:/visites";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        visiteRepository.deleteById(id);
        return "redirect:/visites";
    }
}
