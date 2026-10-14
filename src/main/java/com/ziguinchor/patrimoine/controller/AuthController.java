package com.ziguinchor.patrimoine.controller;

import com.ziguinchor.patrimoine.model.Role;
import com.ziguinchor.patrimoine.model.User;
import com.ziguinchor.patrimoine.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() { return "auth/login"; }

    @GetMapping("/register")
    public String formulaireInscription(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String inscrire(@Valid @ModelAttribute("user") User user,
                            BindingResult resultat,
                            @RequestParam("roleChoisi") String roleChoisi,
                            Model model) {
        if (resultat.hasErrors()) return "auth/register";

        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("erreurEmail", "Cet email est déjà utilisé.");
            return "auth/register";
        }

        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        user.setRole(Role.valueOf(roleChoisi));
        user.setActif(true);
        userRepository.save(user);

        return "redirect:/login?inscription=succes";
    }
}
