-- V7__create_terrain_table.sql
CREATE TABLE terrains (
    id UUID PRIMARY KEY, -- Clé primaire et étrangère vers la table 'biens'
    type_terrain VARCHAR(50) NOT NULL,
    viabilise BOOLEAN DEFAULT FALSE,
    cloture BOOLEAN DEFAULT FALSE,
    superficie_totale DECIMAL(12, 2) NOT NULL,
    titre_fonce BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_terrain_bien FOREIGN KEY (id) REFERENCES biens(id) ON DELETE CASCADE
);
