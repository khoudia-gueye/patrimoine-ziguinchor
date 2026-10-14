# Gestion du Patrimoine Touristique de la Commune de Ziguinchor

Application monolithique Spring Boot + Thymeleaf + MySQL, conforme au rapport
UML final (Mame Khoudia GUEYE, ) : 5 acteurs avec héritage depuis
Utilisateur (Visiteur, Conservateur, Opérateur touristique, Administrateur
communal, Super administrateur).

## Fonctionnalités actuelles

- **Sites touristiques** : CRUD complet (réservé au Conservateur), recherche, avis
- **5 rôles** avec authentification Spring Security (BCrypt)
- **Circuits** : proposés par l'Opérateur touristique (brouillon → publié)
- **Visites** : planifiées par l'Opérateur touristique, liées à un site (et éventuellement un circuit)
- **Réservations** : un Visiteur réserve une place, QR code généré automatiquement, gestion du stock de places, message d'attente si complet, annulation possible

## Lancer le projet

1. Démarrer MySQL dans XAMPP
2. Créer la base `patrimoine_ziguinchor` dans phpMyAdmin (ou laisser `createDatabaseIfNotExist=true` s'en charger)
3. `mvn spring-boot:run` (ou bouton Run dans IntelliJ/VS Code)
4. Ouvrir `http://localhost:8080`

## Comptes de test (mot de passe : `admin123` pour tous)

| Rôle | Email |
|---|---|
| Visiteur | visiteur@ziguinchor.sn |
| Conservateur | conservateur@ziguinchor.sn |
| Opérateur touristique | operateur@ziguinchor.sn |
| Administrateur communal | admincommunal@ziguinchor.sn |
| Super admin | superadmin@ziguinchor.sn |

## Ce qui reste à faire (par rapport au rapport complet)

- Inventaire (Conservateur)
- Maintenance (Conservateur)
- Rapport / statistiques (Administrateur communal)
- Notification
- Signalement d'avis (Visiteur signale, Guide/Opérateur répond)
- Validation des comptes Opérateur/Guide par un administrateur

## Profils de base de données

| Profil | Base | Usage |
|---|---|---|
| *(aucun, par défaut)* | MySQL (XAMPP) | Développement sur le PC |
| `prod` | PostgreSQL hébergé | Mise en ligne (Render) |
| `h2` | H2 en mémoire | Démo sans base externe (données perdues à chaque redémarrage) |

Le profil se choisit avec la variable d'environnement `SPRING_PROFILES_ACTIVE` (`prod` ou `h2`).

## Déploiement sur Render (Docker)

1. Créer une base **PostgreSQL** sur Render.
2. Créer un **Web Service** relié à ce dépôt GitHub (environnement **Docker**).
3. Ajouter les variables d'environnement du service :
   - `DB_URL` : `jdbc:postgresql://HOTE:5432/NOM_BASE`
   - `DB_USER` : utilisateur de la base
   - `DB_PASSWORD` : mot de passe de la base
4. Pour basculer en démo H2 : `SPRING_PROFILES_ACTIVE=h2` (les 3 variables `DB_*` ne sont alors plus nécessaires).
