package repository;

import database.ConnectionFactory;
import model.Corrida;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CorridaRepositoryDB implements IRepository<Corrida> {

    @Override
    public void cadastrar(Corrida corrida) {
        String sql = "INSERT INTO corrida (motorista_id, passageiro_id, origem, destino, distancia, valor, data_hora, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, corrida.getMotoristaId());
            stmt.setInt(2, corrida.getPassageiroId());
            stmt.setString(3, corrida.getOrigem());
            stmt.setString(4, corrida.getDestino());
            stmt.setDouble(5, corrida.getDistancia());
            stmt.setDouble(6, corrida.getValor());
            stmt.setTimestamp(7, Timestamp.valueOf(corrida.getDataHora()));
            stmt.setString(8, corrida.getStatus());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    corrida.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar corrida: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Corrida> listar() {
        String sql = "SELECT * FROM corrida";
        List<Corrida> corridas = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Corrida corrida = new Corrida();
                corrida.setId(rs.getInt("id"));
                corrida.setMotoristaId(rs.getInt("motorista_id"));
                corrida.setPassageiroId(rs.getInt("passageiro_id"));
                corrida.setOrigem(rs.getString("origem"));
                corrida.setDestino(rs.getString("destino"));
                corrida.setDistancia(rs.getDouble("distancia"));
                corrida.setValor(rs.getDouble("valor"));
                
                Timestamp timestamp = rs.getTimestamp("data_hora");
                if (timestamp != null) {
                    corrida.setDataHora(timestamp.toLocalDateTime());
                }
                
                corrida.setStatus(rs.getString("status"));
                corridas.add(corrida);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar corridas: " + e.getMessage(), e);
        }
        
        return corridas;
    }

    @Override
    public void atualizar(Corrida corrida) {
        String sql = "UPDATE corrida SET motorista_id = ?, passageiro_id = ?, origem = ?, destino = ?, distancia = ?, valor = ?, data_hora = ?, status = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, corrida.getMotoristaId());
            stmt.setInt(2, corrida.getPassageiroId());
            stmt.setString(3, corrida.getOrigem());
            stmt.setString(4, corrida.getDestino());
            stmt.setDouble(5, corrida.getDistancia());
            stmt.setDouble(6, corrida.getValor());
            stmt.setTimestamp(7, Timestamp.valueOf(corrida.getDataHora()));
            stmt.setString(8, corrida.getStatus());
            stmt.setInt(9, corrida.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar corrida: " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM corrida WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover corrida: " + e.getMessage(), e);
        }
    }

    @Override
    public Corrida pesquisar(int id) {
        String sql = "SELECT * FROM corrida WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Corrida corrida = new Corrida();
                    corrida.setId(rs.getInt("id"));
                    corrida.setMotoristaId(rs.getInt("motorista_id"));
                    corrida.setPassageiroId(rs.getInt("passageiro_id"));
                    corrida.setOrigem(rs.getString("origem"));
                    corrida.setDestino(rs.getString("destino"));
                    corrida.setDistancia(rs.getDouble("distancia"));
                    corrida.setValor(rs.getDouble("valor"));
                    
                    Timestamp timestamp = rs.getTimestamp("data_hora");
                    if (timestamp != null) {
                        corrida.setDataHora(timestamp.toLocalDateTime());
                    }
                    
                    corrida.setStatus(rs.getString("status"));
                    return corrida;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar corrida: " + e.getMessage(), e);
        }
        
        return null;
    }
}

