-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS distinct_subsequences_db;

-- Usar la base de datos recién creada
USE distinct_subsequences_db;

-- Crear la tabla con los campos especificados
CREATE TABLE IF NOT EXISTS subsequences (
    id INT AUTO_INCREMENT PRIMARY KEY,
    source_initial VARCHAR(255) NOT NULL,
    target_final VARCHAR(255) NOT NULL,
    number_subsequence INT NOT NULL,
    date_create TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Insertar 5 registros en la tabla subsequences
INSERT INTO subsequences (source_initial, target_final, number_subsequence)
VALUES 
('babgbag', 'bag', 5),
('rabbbit', 'rabbit', 3),
('abcde', 'ace', 1),
('aaaaa', 'aa', 10),
('geeksforgeeks', 'gks', 9);