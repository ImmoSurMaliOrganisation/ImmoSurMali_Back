package com.immobilier.plateforme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

// TODO: REMETTRE LA BASE DE DONNÉES - Exclu temporairement pour le test du serveur,
// à supprimer dès que l'application.properties sera configuré pour PostgreSQL.
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PlateformeImmobiliereApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlateformeImmobiliereApplication.class, args);
	}

}
