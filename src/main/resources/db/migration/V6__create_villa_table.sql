
-- V6__create_villa_table.sql

CREATE TABLE villas (
    -- On utilise le type UUID pour correspondre à la table biens
    id UUID PRIMARY KEY,
    surface_terrain DECIMAL(10, 2) NOT NULL,
    nombre_facades INT DEFAULT 4,
    jardin BOOLEAN DEFAULT FALSE,
    surface_jardin DECIMAL(10, 2),
    piscine BOOLEAN DEFAULT FALSE,
    garage BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_villa_bien FOREIGN KEY (id) REFERENCES biens(id) ON DELETE CASCADE
);
