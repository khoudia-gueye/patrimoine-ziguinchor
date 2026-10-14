package com.ziguinchor.patrimoine.controller;

import com.ziguinchor.patrimoine.service.CategorieService;
import com.ziguinchor.patrimoine.service.SiteTouristiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final SiteTouristiqueService siteTouristiqueService;
    private final CategorieService categorieService;

    @Autowired
    public HomeController(SiteTouristiqueService siteTouristiqueService, CategorieService categorieService) {
        this.siteTouristiqueService = siteTouristiqueService;
        this.categorieService = categorieService;
    }

    @GetMapping("/")
    public String accueil(@RequestParam(required = false) String recherche, Model model) {
        if (recherche != null && !recherche.isBlank()) {
            model.addAttribute("sites", siteTouristiqueService.rechercherParNom(recherche));
        } else {
            model.addAttribute("sites", siteTouristiqueService.listerTous());
        }
        model.addAttribute("categories", categorieService.listerToutes());
        model.addAttribute("recherche", recherche);
        return "index";
    }
}
