package repository;

import database.ConnectionFactory;
import model.Pagamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagamentoRepositoryDB implements IRepository<Pagamento> {

    @Override
    public void cadastrar(Pagamento pagamento) {
        String sql = "INSERT INTO pagamento (corrida_id, valor, metodo_pagamento, data_pagamento, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, pagamento.getCorridaId());
            stmt.setDouble(2, pagamento.getValor());
            stmt.setString(3, pagamento.getMetodoPagamento());
            stmt.setTimestamp(4, Timestamp.valueOf(pagamento.getDataPagamento()));
            stmt.setString(5, pagamento.getStatus());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pagamento.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar pagamento: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pagamento> listar() {
        String sql = "SELECT * FROM pagamento";
        List<Pagamento> pagamentos = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Pagamento pagamento = new Pagamento();
                pagamento.setId(rs.getInt("id"));
                pagamento.setCorridaId(rs.getInt("corrida_id"));
                pagamento.setValor(rs.getDouble("valor"));
                pagamento.setMetodoPagamento(rs.getString("metodo_pagamento"));
                
                Timestamp timestamp = rs.getTimestamp("data_pagamento");
                if (timestamp != null) {
                    pagamento.setDataPagamento(timestamp.toLocalDateTime());
                }
                
                pagamento.setStatus(rs.getString("status"));
                pagamentos.add(pagamento);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pagamentos: " + e.getMessage(), e);
        }
        
        return pagamentos;
    }

    @Override
    public void atualizar(Pagamento pagamento) {
        String sql = "UPDATE pagamento SET corrida_id = ?, valor = ?, metodo_pagamento = ?, data_pagamento = ?, status = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, pagamento.getCorridaId());
            stmt.setDouble(2, pagamento.getValor());
            stmt.setString(3, pagamento.getMetodoPagamento());
            stmt.setTimestamp(4, Timestamp.valueOf(pagamento.getDataPagamento()));
            stmt.setString(5, pagamento.getStatus());
            stmt.setInt(6, pagamento.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pagamento: " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM pagamento WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover pagamento: " + e.getMessage(), e);
        }
    }

    @Override
    public Pagamento pesquisar(int id) {
        String sql = "SELECT * FROM pagamento WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pagamento pagamento = new Pagamento();
                    pagamento.setId(rs.getInt("id"));
                    pagamento.setCorridaId(rs.getInt("corrida_id"));
                    pagamento.setValor(rs.getDouble("valor"));
                    pagamento.setMetodoPagamento(rs.getString("metodo_pagamento"));
                    
                    Timestamp timestamp = rs.getTimestamp("data_pagamento");
                    if (timestamp != null) {
                        pagamento.setDataPagamento(timestamp.toLocalDateTime());
                    }
                    
                    pagamento.setStatus(rs.getString("status"));
                    return pagamento;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar pagamento: " + e.getMessage(), e);
        }
        
        return null;
    }
}

