# ImmoSurMali - Back-End 🏠⚙️

API REST back-end de la plateforme SaaS immobilière **ImmoSurMali**, développée avec **Spring Boot**.

## 🛠️ Stack technique

* **Langage :** Java 26
* **Framework :** Spring Boot 4.1.1
* **Persistance :** Spring Data JPA / Hibernate
* **Sécurité :** Spring Security
* **Documentation API :** Springdoc OpenAPI (Swagger)
* **Base de données :** PostgreSQL
* **Outils :** Maven, SDKMAN

## 🚀 Guide de démarrage

### 1. Cloner le projet

```bash
git clone <url-du-repo>
cd ImmoSurMali_Back
```

### 2. Lancer l'application

Utilisez le wrapper Maven inclus pour démarrer le serveur en mode développement :

```bash
./mvnw spring-boot:run
```

> **Windows :** utilisez `mvnw.cmd spring-boot:run`.

## 📚 Endpoints & documentation

### Swagger UI

La documentation interactive de l'API, générée avec Swagger/OpenAPI, est accessible directement dans votre navigateur lorsque l'application est lancée.

**URL :**

```text
http://localhost:8080/swagger-ui/index.html
```

### Endpoint de test

Un endpoint de vérification est disponible pour s'assurer que le serveur fonctionne correctement et que les configurations CORS sont actives.

| Méthode | Route       | Description                       |
| ------- | ----------- | --------------------------------- |
| `GET`   | `/api/test` | Vérifie que le serveur fonctionne |

**URL complète :**

```text
http://localhost:8080/api/test
```

**Réponse attendue :**

```text
Test ok, serveur is running…
```

## 📌 Prérequis

Avant de lancer le projet, assurez-vous d'avoir installé :

* Java 26
* PostgreSQL
* Maven ou le Maven Wrapper fourni avec le projet
* SDKMAN (optionnel)

## 🗄️ Configuration de la base de données

Configurez les paramètres de connexion PostgreSQL dans le fichier de configuration de l'application, par exemple :

Pas encore configurer

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/<database>
spring.datasource.username=<username>
spring.datasource.password=<password>
```
Adaptez ces valeurs à votre environnement local.


## 👨‍💻 Développement

Une fois l'application démarrée, vous pouvez :

1. Accéder à Swagger UI pour consulter et tester les endpoints.
2. Vérifier le fonctionnement du serveur via `/api/test`.
3. Développer et tester les nouvelles fonctionnalités.
