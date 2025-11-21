-- Script para adicionar a coluna 'email' na tabela 'motorista'
-- Execute este script no seu banco de dados MySQL

USE uber;

ALTER TABLE motorista 
ADD COLUMN email VARCHAR(100) AFTER telefone;

