-- ===================================================================
-- V1 : Création de la table des utilisateurs initiale
-- ===================================================================

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       nom VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       mot_de_passe VARCHAR(255) NOT NULL,
                       telephone VARCHAR(50),
                       is_verifier BOOLEAN DEFAULT FALSE,
                       role VARCHAR(50) NOT NULL,
                       user_statut VARCHAR(50) NOT NULL,
                       date_creation TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);