package com.ziguinchor.patrimoine.controller;

import com.ziguinchor.patrimoine.model.Categorie;
import com.ziguinchor.patrimoine.service.CategorieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategorieController {

    private final CategorieService categorieService;

    @Autowired
    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("categories", categorieService.listerToutes());
        return "categories/liste";
    }

    @GetMapping("/nouveau")
    public String formulaireCreation(Model model) {
        model.addAttribute("categorie", new Categorie());
        return "categories/form";
    }

    @GetMapping("/modifier/{id}")
    public String formulaireModification(@PathVariable Long id, Model model) {
        model.addAttribute("categorie", categorieService.trouverParId(id));
        return "categories/form";
    }

    @PostMapping("/enregistrer")
    public String enregistrer(@Valid @ModelAttribute("categorie") Categorie categorie, BindingResult resultat) {
        if (resultat.hasErrors()) {
            return "categories/form";
        }
        categorieService.enregistrer(categorie);
        return "redirect:/categories";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        categorieService.supprimer(id);
        return "redirect:/categories";
    }
}
