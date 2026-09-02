package com.immobilier.plateforme.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Pointe vers le dossier "uploads" situé à la racine de votre projet backend
        Path uploadDir = Paths.get("uploads");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        // 🔍 TRÈS UTILE : Affiche le chemin exact dans votre terminal au lancement
        System.out.println("=== DOSSIER UPLOADS CONFIGURÉ : " + uploadPath + " ===");

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}