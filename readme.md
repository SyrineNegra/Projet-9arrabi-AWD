# 9arrabi

9arrabi est une application web qui facilite la recherche de produits et de services locaux. Elle repose sur une architecture en microservices.

## Services

| Service | Description | Membre |
|---|---|---|
| Profil | Comptes, authentification et profils des clients et prestataires | Mariem |
| Catalogue | Publication et consultation des produits et services | Syrine |
| Localisation | Adresses et recherche d'offres à proximité | Ahmed |
| Avis | Notes et commentaires après une commande ou une prestation | Mahdi |
| Commande | Achats, paiement et suivi des commandes | Nesrine |
| Réservation | Créneaux et réservations des services | Eya |
| Dashboard | Statistiques en lecture seule pour prestataires et administrateur | Tous |

## Architecture

![Architecture](documentation/diagrams/architecture9arrabi.png)

## Technologies

- Back-end : Spring Boot, MySQL
- Dashboard : Python, PostgreSQL, Next.js
- Front-end : Angular, Bootstrap
- Documentation API : Springdoc OpenAPI (Swagger UI)
- Déploiement : Docker, Docker Compose

## Lancement

Prérequis : IntelliJ


## Accès

| Composant | URL |
|---|---|
| Front-end (Angular) | http://localhost:4200 |
| API Gateway | http://localhost:8080 |
| Dashboard | http://localhost:3000 |
