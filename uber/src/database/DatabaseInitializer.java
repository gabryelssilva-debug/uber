package database;

import java.sql.*;

public class DatabaseInitializer {
    
    public static void inicializar() {
        try {
            // Primeiro, conectar sem especificar o banco para criar se não existir
            String urlSemBanco = "jdbc:mysql://localhost:3306/";
            Connection conn = DriverManager.getConnection(urlSemBanco, ConnectionFactory.getUsuario(), ConnectionFactory.getSenha());
            
            // Criar o banco de dados se não existir
            criarBancoSeNaoExistir(conn);
            conn.close();
            
            // Agora conectar ao banco específico
            conn = ConnectionFactory.getConnection();
            
            // Criar todas as tabelas se não existirem
            criarTabelaMotorista(conn);
            criarTabelaPassageiro(conn);
            criarTabelaCarro(conn);
            criarTabelaCorrida(conn);
            criarTabelaPagamento(conn);
            
            // Verificar e adicionar colunas faltantes se necessário
            verificarECriarColunasMotorista(conn);
            
            // Atualizar tamanho das colunas telefone se necessário
            atualizarTamanhoColuna(conn, "motorista", "telefone", "VARCHAR(30)");
            atualizarTamanhoColuna(conn, "passageiro", "telefone", "VARCHAR(30)");
            
            conn.close();
            System.out.println("Banco de dados inicializado com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void criarBancoSeNaoExistir(Connection conn) throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS uber CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
    
    private static void criarTabelaMotorista(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS motorista (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(100) NOT NULL, " +
                    "cpf VARCHAR(14) NOT NULL, " +
                    "telefone VARCHAR(30), " +
                    "email VARCHAR(100), " +
                    "cnh VARCHAR(20) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
        
        // Atualizar coluna telefone se já existir com tamanho menor
        atualizarTamanhoColuna(conn, "motorista", "telefone", "VARCHAR(30)");
    }
    
    private static void criarTabelaPassageiro(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS passageiro (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(100) NOT NULL, " +
                    "cpf VARCHAR(14) NOT NULL, " +
                    "telefone VARCHAR(30), " +
                    "email VARCHAR(100)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
        
        // Atualizar coluna telefone se já existir com tamanho menor
        atualizarTamanhoColuna(conn, "passageiro", "telefone", "VARCHAR(30)");
    }
    
    private static void criarTabelaCarro(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS carro (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "motorista_id INT NOT NULL, " +
                    "placa VARCHAR(10) NOT NULL UNIQUE, " +
                    "modelo VARCHAR(50) NOT NULL, " +
                    "marca VARCHAR(50) NOT NULL, " +
                    "ano INT NOT NULL, " +
                    "cor VARCHAR(30), " +
                    "FOREIGN KEY (motorista_id) REFERENCES motorista(id) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
    
    private static void criarTabelaCorrida(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS corrida (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "motorista_id INT NOT NULL, " +
                    "passageiro_id INT NOT NULL, " +
                    "origem VARCHAR(200) NOT NULL, " +
                    "destino VARCHAR(200) NOT NULL, " +
                    "distancia DOUBLE NOT NULL, " +
                    "valor DOUBLE NOT NULL, " +
                    "data_hora DATETIME NOT NULL, " +
                    "status VARCHAR(20) NOT NULL DEFAULT 'Pendente', " +
                    "FOREIGN KEY (motorista_id) REFERENCES motorista(id) " +
                    "ON DELETE RESTRICT ON UPDATE CASCADE, " +
                    "FOREIGN KEY (passageiro_id) REFERENCES passageiro(id) " +
                    "ON DELETE RESTRICT ON UPDATE CASCADE" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
    
    private static void criarTabelaPagamento(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS pagamento (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "corrida_id INT NOT NULL, " +
                    "valor DOUBLE NOT NULL, " +
                    "metodo_pagamento VARCHAR(50) NOT NULL, " +
                    "data_pagamento DATETIME NOT NULL, " +
                    "status VARCHAR(20) NOT NULL DEFAULT 'Pendente', " +
                    "FOREIGN KEY (corrida_id) REFERENCES corrida(id) " +
                    "ON DELETE RESTRICT ON UPDATE CASCADE" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
    
    private static void verificarECriarColunasMotorista(Connection conn) throws SQLException {
        // Verificar e adicionar todas as colunas necessárias na ordem correta
        // Primeiro email (depois de telefone)
        verificarECriarColuna(conn, "motorista", "email", "VARCHAR(100)", "telefone");
        // Depois cnh (no final, ou depois de email se email existir)
        verificarECriarColuna(conn, "motorista", "cnh", "VARCHAR(20)", "email");
    }
    
    private static void verificarECriarColuna(Connection conn, String tabela, String coluna, String tipo, String posicao) throws SQLException {
        // Verificar se a coluna existe
        String sql = "SELECT COUNT(*) as count FROM information_schema.COLUMNS " +
                    "WHERE TABLE_SCHEMA = 'uber' " +
                    "AND TABLE_NAME = ? " +
                    "AND COLUMN_NAME = ?";
        
        boolean colunaExiste = false;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tabela);
            stmt.setString(2, coluna);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    colunaExiste = rs.getInt("count") > 0;
                }
            }
        } catch (SQLException e) {
            // Se der erro, assumir que não existe
            colunaExiste = false;
        }
        
        // Se a coluna não existir, adicionar
        if (!colunaExiste) {
            try {
                String alterSql;
                if (posicao != null && !posicao.isEmpty()) {
                    // Verificar se a coluna de posição existe antes de usar AFTER
                    String verificarPosicao = "SELECT COUNT(*) as count FROM information_schema.COLUMNS " +
                                              "WHERE TABLE_SCHEMA = 'uber' " +
                                              "AND TABLE_NAME = ? " +
                                              "AND COLUMN_NAME = ?";
                    boolean posicaoExiste = false;
                    try (PreparedStatement stmt = conn.prepareStatement(verificarPosicao)) {
                        stmt.setString(1, tabela);
                        stmt.setString(2, posicao);
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) {
                                posicaoExiste = rs.getInt("count") > 0;
                            }
                        }
                    }
                    
                    if (posicaoExiste) {
                        alterSql = String.format("ALTER TABLE %s ADD COLUMN %s %s AFTER %s", tabela, coluna, tipo, posicao);
                    } else {
                        // Se a posição não existir, adicionar no final
                        alterSql = String.format("ALTER TABLE %s ADD COLUMN %s %s", tabela, coluna, tipo);
                    }
                } else {
                    alterSql = String.format("ALTER TABLE %s ADD COLUMN %s %s", tabela, coluna, tipo);
                }
                
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(alterSql);
                    System.out.println(String.format("Coluna '%s' adicionada à tabela '%s' com sucesso!", coluna, tabela));
                }
            } catch (SQLException e) {
                // Se a coluna já existir (erro de concorrência), ignorar
                String errorMsg = e.getMessage();
                if (errorMsg != null && !errorMsg.contains("Duplicate column name") && !errorMsg.contains("already exists")) {
                    System.err.println(String.format("Erro ao adicionar coluna '%s': %s", coluna, errorMsg));
                    // Tentar adicionar sem especificar posição
                    try {
                        String alterSql = String.format("ALTER TABLE %s ADD COLUMN %s %s", tabela, coluna, tipo);
                        try (Statement stmt = conn.createStatement()) {
                            stmt.executeUpdate(alterSql);
                            System.out.println(String.format("Coluna '%s' adicionada à tabela '%s' com sucesso!", coluna, tabela));
                        }
                    } catch (SQLException e2) {
                        if (!e2.getMessage().contains("Duplicate column name") && !e2.getMessage().contains("already exists")) {
                            throw e2;
                        }
                    }
                }
            }
        }
    }
    
    private static void atualizarTamanhoColuna(Connection conn, String tabela, String coluna, String novoTipo) throws SQLException {
        try {
            // Verificar o tamanho atual da coluna
            String sql = "SELECT COLUMN_TYPE FROM information_schema.COLUMNS " +
                        "WHERE TABLE_SCHEMA = 'uber' " +
                        "AND TABLE_NAME = ? " +
                        "AND COLUMN_NAME = ?";
            
            String tipoAtual = null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, tabela);
                stmt.setString(2, coluna);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        tipoAtual = rs.getString("COLUMN_TYPE");
                    }
                }
            }
            
            // Se a coluna existir e o tipo for diferente, atualizar
            if (tipoAtual != null && !tipoAtual.equalsIgnoreCase(novoTipo)) {
                String alterSql = String.format("ALTER TABLE %s MODIFY COLUMN %s %s", tabela, coluna, novoTipo);
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(alterSql);
                    System.out.println(String.format("Coluna '%s' da tabela '%s' atualizada para %s!", coluna, tabela, novoTipo));
                }
            }
        } catch (SQLException e) {
            // Ignorar erros ao atualizar tamanho da coluna
            System.err.println(String.format("Aviso: Não foi possível atualizar coluna '%s': %s", coluna, e.getMessage()));
        }
    }
}

