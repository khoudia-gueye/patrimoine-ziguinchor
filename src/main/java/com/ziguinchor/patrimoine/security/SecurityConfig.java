package com.ziguinchor.patrimoine.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/", "/sites", "/sites/{id:[0-9]+}", "/categories",
                        "/visites", "/circuits", "/login", "/register", "/css/**", "/images/**",
                        "/h2-console/**"
                ).permitAll()

                .requestMatchers(
                        "/sites/nouveau", "/sites/enregistrer",
                        "/sites/modifier/**", "/sites/supprimer/**",
                        "/categories/nouveau", "/categories/enregistrer",
                        "/categories/modifier/**", "/categories/supprimer/**"
                ).hasRole("CONSERVATEUR")

                .requestMatchers(
                        "/circuits/nouveau", "/circuits/enregistrer", "/circuits/mes-circuits",
                        "/circuits/publier/**", "/circuits/supprimer/**",
                        "/visites/planifier", "/visites/enregistrer", "/visites/supprimer/**"
                ).hasRole("OPERATEUR_TOURISTIQUE")

                .requestMatchers("/reservations/**").hasRole("VISITEUR")

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

        return http.build();
    }
}
