-- 1. Table parente (Commune à tous les biens)
CREATE TABLE biens (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       titre VARCHAR(255) NOT NULL,
                       description TEXT,
                       prix DECIMAL(12, 2) NOT NULL,
                       surface_habitable DECIMAL(10, 2),
                       statut VARCHAR(50) NOT NULL DEFAULT 'DISPONIBLE',

    -- Localisation ordonnée
                       ville VARCHAR(100) NOT NULL,
                       quartier VARCHAR(150) NOT NULL,
                       adresse VARCHAR(255),
                       latitude DOUBLE PRECISION,
                       longitude DOUBLE PRECISION,

                       proprietaire_id BIGINT NOT NULL, -- Aligné sur l'ID de la table users
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_bien_proprietaire FOREIGN KEY (proprietaire_id) REFERENCES users(id)
);

-- 2. Table enfant (Spécifique aux appartements)
CREATE TABLE appartements (
                              id UUID PRIMARY KEY, -- Clé primaire et étrangère vers biens(id)
                              nombre_chambres INT,
                              est_meuble BOOLEAN DEFAULT FALSE,
                              etage INT,
                              ascenseur BOOLEAN DEFAULT FALSE,
                              piscine BOOLEAN DEFAULT FALSE,
                              charges_mensuelles DECIMAL(10, 2),
                              surface_balcon DECIMAL(10, 2),
                              parking_inclus BOOLEAN DEFAULT FALSE,
                              CONSTRAINT fk_appartement_bien FOREIGN KEY (id) REFERENCES biens(id) ON DELETE CASCADE
);

-- 3. Table des médias (Photos / Fichiers)
CREATE TABLE medias (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        url VARCHAR(255) NOT NULL,
                        type VARCHAR(50) DEFAULT 'IMAGE',
                        est_principal BOOLEAN DEFAULT FALSE,
                        bien_id UUID NOT NULL, -- Corrigé en UUID pour correspondre à biens(id)
                        CONSTRAINT fk_media_bien FOREIGN KEY (bien_id) REFERENCES biens(id) ON DELETE CASCADE
);