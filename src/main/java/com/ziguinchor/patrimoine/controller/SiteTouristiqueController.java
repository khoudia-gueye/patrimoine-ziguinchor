package com.ziguinchor.patrimoine.controller;

import com.ziguinchor.patrimoine.model.Avis;
import com.ziguinchor.patrimoine.model.SiteTouristique;
import com.ziguinchor.patrimoine.service.AvisService;
import com.ziguinchor.patrimoine.service.CategorieService;
import com.ziguinchor.patrimoine.service.SiteTouristiqueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sites")
public class SiteTouristiqueController {

    private final SiteTouristiqueService siteTouristiqueService;
    private final CategorieService categorieService;
    private final AvisService avisService;

    @Autowired
    public SiteTouristiqueController(SiteTouristiqueService siteTouristiqueService,
                                      CategorieService categorieService,
                                      AvisService avisService) {
        this.siteTouristiqueService = siteTouristiqueService;
        this.categorieService = categorieService;
        this.avisService = avisService;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("sites", siteTouristiqueService.listerTous());
        return "sites/liste";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        SiteTouristique site = siteTouristiqueService.trouverParId(id);
        model.addAttribute("site", site);
        model.addAttribute("nouvelAvis", new Avis());
        return "sites/detail";
    }

    @GetMapping("/nouveau")
    public String formulaireCreation(Model model) {
        model.addAttribute("site", new SiteTouristique());
        model.addAttribute("categories", categorieService.listerToutes());
        return "sites/form";
    }

    @GetMapping("/modifier/{id}")
    public String formulaireModification(@PathVariable Long id, Model model) {
        model.addAttribute("site", siteTouristiqueService.trouverParId(id));
        model.addAttribute("categories", categorieService.listerToutes());
        return "sites/form";
    }

    @PostMapping("/enregistrer")
    public String enregistrer(@Valid @ModelAttribute("site") SiteTouristique site,
                               BindingResult resultat,
                               Model model) {
        if (resultat.hasErrors()) {
            model.addAttribute("categories", categorieService.listerToutes());
            return "sites/form";
        }
        siteTouristiqueService.enregistrer(site);
        return "redirect:/sites";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        siteTouristiqueService.supprimer(id);
        return "redirect:/sites";
    }

    @PostMapping("/{id}/avis")
    public String ajouterAvis(@PathVariable Long id, @ModelAttribute("nouvelAvis") Avis avis) {
        SiteTouristique site = siteTouristiqueService.trouverParId(id);
        avis.setSiteTouristique(site);
        avisService.enregistrer(avis);
        return "redirect:/sites/" + id;
    }
}
