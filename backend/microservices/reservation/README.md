# 📚 Microservice Réservation - 9arrabi

Microservice Spring Boot pour la gestion des créneaux et des réservations de services dans l'architecture distribuée **9arrabi**.

## Spécifications Techniques

- **Port** : `8086`
- **Base de données** : H2 en mémoire (`jdbc:h2:mem:reservationdb`)
- **Console H2** : `http://localhost:8086/h2-console` (User: `sa`, sans mot de passe)
- **Déploiement** : Dockerfile & Docker Compose

## API Endpoints Réservations

| Requête | URI | Code Statut |
| :--- | :--- | :--- |
| **GET** | `/api/services/{id}/reservations` | 200 OK ou 404 Not found |
| **POST** | `/api/services/{id}/reservations` | 201 Created ou 400 Bad Request |
| **PUT** | `/api/services/{id}/reservations` ou `/{reservationId}` | 200 OK ou 404 Not found |
| **DELETE** | `/api/services/{id}/reservations/supprimer` ou `/supprimer/{reservationId}` | 204 No Content |
| **PATCH** | `/api/services/{id}/reservations/{resId}/confirmer` | 200 OK |
| **PATCH** | `/api/services/{id}/reservations/{resId}/annuler` | 200 OK |

## API Endpoints Créneaux

| Requête | URI | Description |
| :--- | :--- | :--- |
| **GET** | `/api/services/{id}/creneaux` | Liste tous les créneaux d'un service |
| **GET** | `/api/services/{id}/creneaux/disponibles` | Liste les créneaux disponibles (`disponible=true`) |
| **POST** | `/api/services/{id}/creneaux` | Ajoute un créneau pour le service |
| **GET** | `/api/services/creneaux/{creneauId}/disponibilite` | Vérifie la disponibilité d'un créneau |

## Démarrage

### Local (Maven)
```bash
./mvnw spring-boot:run
```

### Docker
```bash
docker compose up --build -d
```
