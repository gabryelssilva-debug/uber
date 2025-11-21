CREATE DATABASE IF NOT EXISTS uber 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE uber;

CREATE TABLE IF NOT EXISTS motorista (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    telefone VARCHAR(30),
    email VARCHAR(100),
    cnh VARCHAR(20) NOT NULL,
    INDEX idx_cpf (cpf),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS passageiro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    telefone VARCHAR(30),
    email VARCHAR(100),
    INDEX idx_cpf (cpf),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS carro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    motorista_id INT NOT NULL,
    placa VARCHAR(10) NOT NULL UNIQUE,
    modelo VARCHAR(50) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    cor VARCHAR(30),
    FOREIGN KEY (motorista_id) REFERENCES motorista(id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    INDEX idx_motorista (motorista_id),
    INDEX idx_placa (placa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS corrida (
    id INT AUTO_INCREMENT PRIMARY KEY,
    motorista_id INT NOT NULL,
    passageiro_id INT NOT NULL,
    origem VARCHAR(200) NOT NULL,
    destino VARCHAR(200) NOT NULL,
    distancia DOUBLE NOT NULL,
    valor DOUBLE NOT NULL,
    data_hora DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Pendente',
    FOREIGN KEY (motorista_id) REFERENCES motorista(id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    FOREIGN KEY (passageiro_id) REFERENCES passageiro(id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    INDEX idx_motorista (motorista_id),
    INDEX idx_passageiro (passageiro_id),
    INDEX idx_data_hora (data_hora),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS pagamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    corrida_id INT NOT NULL,
    valor DOUBLE NOT NULL,
    metodo_pagamento VARCHAR(50) NOT NULL,
    data_pagamento DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Pendente',
    FOREIGN KEY (corrida_id) REFERENCES corrida(id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    INDEX idx_corrida (corrida_id),
    INDEX idx_data_pagamento (data_pagamento),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
