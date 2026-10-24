-- Script para crear la base de datos del proyecto
CREATE DATABASE IF NOT EXISTS Libreria;

USE Libreria;

CREATE TABLE IF NOT EXISTS Libro (
    id     INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100),
    autor  VARCHAR(60),
    precio DECIMAL(10,2)
);
