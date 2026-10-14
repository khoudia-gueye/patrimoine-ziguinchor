package com.ziguinchor.patrimoine.config;

import com.ziguinchor.patrimoine.model.*;
import com.ziguinchor.patrimoine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * S'exécute une fois au démarrage : crée les comptes de test (un par rôle),
 * les données de départ (catégories, sites, avis), ainsi que des circuits,
 * visites et réservations de test — uniquement si elles n'existent pas déjà,
 * pour ne jamais dupliquer avec MySQL.
 *
 * Mot de passe pour TOUS les comptes de test : admin123
 */
@Component
public class DonneesInitiales implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategorieRepository categorieRepository;
    private final SiteTouristiqueRepository siteRepository;
    private final AvisRepository avisRepository;
    private final CircuitRepository circuitRepository;
    private final VisiteRepository visiteRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public DonneesInitiales(UserRepository userRepository, PasswordEncoder passwordEncoder,
                            CategorieRepository categorieRepository,
                            SiteTouristiqueRepository siteRepository,
                            AvisRepository avisRepository,
                            CircuitRepository circuitRepository,
                            VisiteRepository visiteRepository,
                            ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categorieRepository = categorieRepository;
        this.siteRepository = siteRepository;
        this.avisRepository = avisRepository;
        this.circuitRepository = circuitRepository;
        this.visiteRepository = visiteRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) {
        creerComptesDeTest();
        creerDonneesTouristiquesSiAbsentes();
        creerCircuitsVisitesEtReservationsSiAbsents();
    }

    private void creerComptesDeTest() {
        creerCompteSiAbsent("conservateur@ziguinchor.sn", "Conservateur Test", Role.CONSERVATEUR);
        creerCompteSiAbsent("operateur@ziguinchor.sn", "Opérateur Touristique Test", Role.OPERATEUR_TOURISTIQUE);
        creerCompteSiAbsent("admincommunal@ziguinchor.sn", "Administrateur Communal Test", Role.ADMINISTRATEUR_COMMUNAL);
        creerCompteSiAbsent("superadmin@ziguinchor.sn", "Super Admin Test", Role.SUPER_ADMIN);
        creerCompteSiAbsent("visiteur@ziguinchor.sn", "Visiteur Test", Role.VISITEUR);
    }

    private void creerCompteSiAbsent(String email, String nom, Role role) {
        if (!userRepository.existsByEmail(email)) {
            User u = new User();
            u.setNom(nom);
            u.setEmail(email);
            u.setMotDePasse(passwordEncoder.encode("admin123"));
            u.setRole(role);
            u.setActif(true);
            userRepository.save(u);
        }
    }

    private void creerDonneesTouristiquesSiAbsentes() {
        if (categorieRepository.count() > 0) return;

        Categorie culturel = categorieRepository.save(new Categorie("Culturel", "Sites liés à la culture et aux traditions locales"));
        Categorie naturel = categorieRepository.save(new Categorie("Naturel", "Sites naturels : fleuve, mangroves, forêts"));
        Categorie religieux = categorieRepository.save(new Categorie("Religieux", "Lieux de culte et sites spirituels"));
        Categorie historique = categorieRepository.save(new Categorie("Historique", "Bâtiments et lieux à valeur historique"));

        SiteTouristique cathedrale = new SiteTouristique();
        cathedrale.setNom("Cathédrale Saint Antoine de Ziguinchor");
        cathedrale.setDescription("Édifice religieux emblématique du centre-ville, connu pour son architecture coloniale.");
        cathedrale.setAdresse("Avenue du Général de Gaulle");
        cathedrale.setQuartier("Centre-ville");
        cathedrale.setImageUrl("https://i.pinimg.com/1200x/3a/89/3c/3a893c24215c16482cdd9aac519af969.jpg");
        cathedrale.setLatitude(12.5665);
        cathedrale.setLongitude(-16.2733);
        cathedrale.setDateAjout(LocalDate.now());
        cathedrale.setCategorie(religieux);
        siteRepository.save(cathedrale);

        SiteTouristique marche = new SiteTouristique();
        marche.setNom("Marché Saint Maur");
        marche.setDescription("Grand marché central animé, lieu privilégié pour découvrir l'artisanat et les produits locaux de Casamance.");
        marche.setAdresse("Centre-ville");
        marche.setQuartier("Escale");
        marche.setImageUrl("https://i.pinimg.com/736x/74/fa/3f/74fa3f23c20d254acac0fa829f23a999.jpg");
        marche.setLatitude(12.5680);
        marche.setLongitude(-16.2721);
        marche.setDateAjout(LocalDate.now());
        marche.setCategorie(culturel);
        siteRepository.save(marche);

        SiteTouristique fleuve = new SiteTouristique();
        fleuve.setNom("Le Fleuve Casamance (rive de Ziguinchor)");
        fleuve.setDescription("Le fleuve qui traverse la ville, propice aux balades en pirogue et à l'observation de la mangrove.");
        fleuve.setAdresse("Rive du fleuve");
        fleuve.setQuartier("Boucotte");
        fleuve.setImageUrl("https://i.pinimg.com/1200x/4c/03/cd/4c03cd9cdf21f957688d01548f9b60ed.jpg");
        fleuve.setLatitude(12.5631);
        fleuve.setLongitude(-16.2660);
        fleuve.setDateAjout(LocalDate.now());
        fleuve.setCategorie(naturel);
        siteRepository.save(fleuve);

        SiteTouristique caseEtage = new SiteTouristique();
        caseEtage.setNom("Case à étage (architecture coloniale)");
        caseEtage.setDescription("Bâtisses traditionnelles à étage datant de la période coloniale, témoins de l'histoire de la ville.");
        caseEtage.setAdresse("Quartier Boucotte");
        caseEtage.setQuartier("Boucotte");
        caseEtage.setImageUrl("https://i.pinimg.com/1200x/23/c1/35/23c1351169f30b1182eb2f3b162328cd.jpg");
        caseEtage.setLatitude(12.5645);
        caseEtage.setLongitude(-16.2705);
        caseEtage.setDateAjout(LocalDate.now());
        caseEtage.setCategorie(historique);
        siteRepository.save(caseEtage);

        SiteTouristique alliance = new SiteTouristique();
        alliance.setNom("Alliance Franco-Sénégalaise de Ziguinchor");
        alliance.setDescription("Centre culturel proposant expositions, spectacles et rencontres artistiques.");
        alliance.setAdresse("Centre-ville");
        alliance.setQuartier("Escale");
        alliance.setImageUrl("https://i.pinimg.com/736x/71/ad/67/71ad6792348bdacdf118b65793826531.jpg");
        alliance.setLatitude(12.5670);
        alliance.setLongitude(-16.2740);
        alliance.setDateAjout(LocalDate.now());
        alliance.setCategorie(culturel);
        siteRepository.save(alliance);

        Avis avis1 = new Avis();
        avis1.setAuteur("Astou D.");
        avis1.setCommentaire("Un site magnifique, à visiter absolument en fin de journée.");
        avis1.setNote(5);
        avis1.setDateAvis(LocalDate.now());
        avis1.setSiteTouristique(cathedrale);
        avisRepository.save(avis1);

        Avis avis2 = new Avis();
        avis2.setAuteur("Moussa S.");
        avis2.setCommentaire("Très animé, bon pour l'artisanat local mais attention à la chaleur en journée.");
        avis2.setNote(4);
        avis2.setDateAvis(LocalDate.now());
        avis2.setSiteTouristique(marche);
        avisRepository.save(avis2);
    }

    private void creerCircuitsVisitesEtReservationsSiAbsents() {
        if (circuitRepository.count() > 0) return;

        // On récupère les sites et l'opérateur créés précédemment
        SiteTouristique cathedrale = siteRepository.findByNom("Cathédrale Saint Antoine de Ziguinchor").orElse(null);
        SiteTouristique marche = siteRepository.findByNom("Marché Saint Maur").orElse(null);
        SiteTouristique fleuve = siteRepository.findByNom("Le Fleuve Casamance (rive de Ziguinchor)").orElse(null);

        User operateur = userRepository.findByEmail("operateur@ziguinchor.sn").orElse(null);
        User touriste = userRepository.findByEmail("visiteur@ziguinchor.sn").orElse(null);

        if (cathedrale == null || marche == null || fleuve == null || operateur == null || touriste == null) {
            return; // sécurité : si un pré-requis manque, on n'insère rien pour éviter une erreur
        }

        // --- Circuit 1 : Découverte culturelle (publié) ---
        Circuit circuitCulturel = new Circuit();
        circuitCulturel.setNom("Découverte culturelle de Ziguinchor");
        circuitCulturel.setDescription("Un circuit d'une journée pour découvrir le patrimoine culturel et religieux du centre-ville.");
        circuitCulturel.setDureeJours(1);
        circuitCulturel.setPrix(15000.0);
        circuitCulturel.setStatut(StatutCircuit.PUBLIE);
        circuitCulturel.setOperateur(operateur);
        circuitRepository.save(circuitCulturel);

        // --- Circuit 2 : Nature et fleuve (publié) ---
        Circuit circuitNature = new Circuit();
        circuitNature.setNom("Balade nature le long du fleuve Casamance");
        circuitNature.setDescription("Circuit d'une demi-journée pour explorer la mangrove et le fleuve en pirogue.");
        circuitNature.setDureeJours(1);
        circuitNature.setPrix(10000.0);
        circuitNature.setStatut(StatutCircuit.PUBLIE);
        circuitNature.setOperateur(operateur);
        circuitRepository.save(circuitNature);

        // --- Circuit 3 : brouillon (pas encore publié, pour tester ce cas) ---
        Circuit circuitBrouillon = new Circuit();
        circuitBrouillon.setNom("Circuit artisanat (en préparation)");
        circuitBrouillon.setDescription("Circuit encore en cours de conception, non publié.");
        circuitBrouillon.setDureeJours(1);
        circuitBrouillon.setPrix(8000.0);
        circuitBrouillon.setStatut(StatutCircuit.BROUILLON);
        circuitBrouillon.setOperateur(operateur);
        circuitRepository.save(circuitBrouillon);

        // --- Visites planifiées, rattachées aux circuits publiés ---
        Visite visite1 = new Visite();
        visite1.setSite(cathedrale);
        visite1.setCircuit(circuitCulturel);
        visite1.setStatut(StatutVisite.PLANIFIEE);
        visite1.setGuide("Ibrahima Sané");
        visite1.setDateHeureDebut(LocalDateTime.now().plusDays(3).withHour(9).withMinute(0));
        visite1.setDureeMinutes(90);
        visite1.setPlacesMax(15);
        visiteRepository.save(visite1);

        Visite visite2 = new Visite();
        visite2.setSite(marche);
        visite2.setCircuit(circuitCulturel);
        visite2.setStatut(StatutVisite.PLANIFIEE);
        visite2.setGuide("Ibrahima Sané");
        visite2.setDateHeureDebut(LocalDateTime.now().plusDays(3).withHour(11).withMinute(0));
        visite2.setDureeMinutes(60);
        visite2.setPlacesMax(15);
        visiteRepository.save(visite2);

        Visite visite3 = new Visite();
        visite3.setSite(fleuve);
        visite3.setCircuit(circuitNature);
        visite3.setStatut(StatutVisite.PLANIFIEE);
        visite3.setGuide("Fatou Diémé");
        visite3.setDateHeureDebut(LocalDateTime.now().plusDays(5).withHour(8).withMinute(30));
        visite3.setDureeMinutes(120);
        visite3.setPlacesMax(10);
        visiteRepository.save(visite3);

        // --- Une visite déjà terminée, pour tester ce statut aussi ---
        Visite visitePassee = new Visite();
        visitePassee.setSite(cathedrale);
        visitePassee.setStatut(StatutVisite.TERMINEE);
        visitePassee.setGuide("Ibrahima Sané");
        // Astuce : @Future empêche une date passée à la validation du formulaire,
        // mais n'empêche pas une insertion directe en base ici.
        visitePassee.setDateHeureDebut(LocalDateTime.now().minusDays(2).withHour(10).withMinute(0));
        visitePassee.setDureeMinutes(90);
        visitePassee.setPlacesMax(15);
        visitePassee.setPlacesReservees(1);
        visiteRepository.save(visitePassee);

        // --- Réservation de test sur la première visite ---
        Reservation reservation1 = new Reservation();
        reservation1.setTouriste(touriste);
        reservation1.setVisite(visite1);
        reservation1.setStatut(StatutReservation.CONFIRME);
        reservation1.setMontant(circuitCulturel.getPrix());
        reservationRepository.save(reservation1);

        visite1.setPlacesReservees(visite1.getPlacesReservees() + 1);
        visiteRepository.save(visite1);

        // --- Réservation liée à la visite déjà terminée ---
        Reservation reservationPassee = new Reservation();
        reservationPassee.setTouriste(touriste);
        reservationPassee.setVisite(visitePassee);
        reservationPassee.setStatut(StatutReservation.CONFIRME);
        reservationPassee.setMontant(5000.0);
        reservationRepository.save(reservationPassee);
    }
}