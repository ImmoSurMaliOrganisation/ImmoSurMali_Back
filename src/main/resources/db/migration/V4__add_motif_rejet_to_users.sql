-- ===================================================================
-- V4 : Ajout de la colonne motif_rejet pour stocker la raison du refus
-- ===================================================================

ALTER TABLE users ADD COLUMN IF NOT EXISTS motif_rejet VARCHAR(500);