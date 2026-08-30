-- ===================================================================
-- V2 : Ajout des index de performance pour la recherche et les filtres
-- ===================================================================

-- 1. Extension pour la recherche partielle rapide (PostgreSQL Trigram)
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- 2. Index pour accélérer le filtre par rôle
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);

-- 3. Index pour accélérer la recherche par email, nom et téléphone
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_nom ON users(nom);
CREATE INDEX IF NOT EXISTS idx_users_telephone ON users(telephone);

-- 4. Index Trigramme pour optimiser les recherches textuelles avec LIKE '%mot%'
CREATE INDEX IF NOT EXISTS idx_users_nom_trgm ON users USING gin (nom gin_trgm_ops);
CREATE INDEX IF NOT EXISTS idx_users_email_trgm ON users USING gin (email gin_trgm_ops);
CREATE INDEX IF NOT EXISTS idx_users_telephone_trgm ON users USING gin (telephone gin_trgm_ops);