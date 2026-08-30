-- ===================================================================
-- V3 : Ajout des colonnes spécifiques aux agences dans la table users
-- ===================================================================

ALTER TABLE users
    ADD COLUMN IF NOT EXISTS nom_agence VARCHAR(255),
    ADD COLUMN IF NOT EXISTS adresse VARCHAR(255),
    ADD COLUMN IF NOT EXISTS rccm VARCHAR(100),
    ADD COLUMN IF NOT EXISTS rccm_document_url VARCHAR(500),
    ADD COLUMN IF NOT EXISTS nif VARCHAR(50),
    ADD COLUMN IF NOT EXISTS nif_document_url VARCHAR(500);

-- Ajout d'une contrainte d'unicité sur le RCCM pour éviter les doublons d'agences
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_rccm ON users(rccm) WHERE rccm IS NOT NULL;