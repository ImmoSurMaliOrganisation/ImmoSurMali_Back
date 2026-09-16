-- V7__create_terrains_table.sql

CREATE TABLE terrains (
                          id UUID NOT NULL,
                          type_terrain VARCHAR(50) NOT NULL,
                          superficie_totale DOUBLE PRECISION NOT NULL,
                          nombre_facades INT DEFAULT 1,
                          zonage VARCHAR(100),
                          viabilise BOOLEAN DEFAULT FALSE,
                          cloture BOOLEAN DEFAULT FALSE,
                          titre_foncier BOOLEAN DEFAULT TRUE,
                          eau BOOLEAN DEFAULT FALSE,
                          electricite BOOLEAN DEFAULT FALSE,
                          acces_goudronne BOOLEAN DEFAULT FALSE,
                          assainissement BOOLEAN DEFAULT FALSE,

    -- Clé primaire et clé étrangère vers la table parente 'biens'
                          CONSTRAINT pk_terrains PRIMARY KEY (id),
                          CONSTRAINT fk_terrain_bien FOREIGN KEY (id) REFERENCES biens(id) ON DELETE CASCADE
);