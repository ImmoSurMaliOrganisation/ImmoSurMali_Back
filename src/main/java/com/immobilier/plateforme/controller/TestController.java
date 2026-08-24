package com.immobilier.plateforme.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequestMapping("/api")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    private final DataSource dataSource;

    // un constructeur 
    public TestController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/test")
    public String testServer() {
        return "Test ok, serveur is running…";
    }

    @GetMapping("/test-db")
    public String testDbConnection() {
        log.info("==============pre (Supprime l'avertissement de VS Code)============================");
        log.info("--> Test de connexion brute à PostgreSQL...");
        try (Connection connection = dataSource.getConnection()) {
            String dbName = connection.getCatalog();
            log.info("--> ✅ Connexion PostgreSQL RÉUSSIE ! Base : {}", dbName);
            log.info("==========================================");
            return "✅ Connexion BDD réussie ! Base de données : " + dbName;
        } catch (Exception e) {
            log.error("--> ❌ ÉCHEC de la connexion !");
            log.error("Détail : {}", e.getMessage());
            log.info("==========================================");
            return "❌ Échecs de connexion BDD : " + e.getMessage();
        }
    }
}
