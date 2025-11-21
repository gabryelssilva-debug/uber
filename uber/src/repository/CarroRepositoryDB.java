package repository;

import database.ConnectionFactory;
import model.Carro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroRepositoryDB implements IRepository<Carro> {

    @Override
    public void cadastrar(Carro carro) {
        String sql = "INSERT INTO carro (motorista_id, placa, modelo, marca, ano, cor) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, carro.getMotoristaId());
            stmt.setString(2, carro.getPlaca());
            stmt.setString(3, carro.getModelo());
            stmt.setString(4, carro.getMarca());
            stmt.setInt(5, carro.getAno());
            stmt.setString(6, carro.getCor());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    carro.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar carro: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Carro> listar() {
        String sql = "SELECT * FROM carro";
        List<Carro> carros = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Carro carro = new Carro();
                carro.setId(rs.getInt("id"));
                carro.setMotoristaId(rs.getInt("motorista_id"));
                carro.setPlaca(rs.getString("placa"));
                carro.setModelo(rs.getString("modelo"));
                carro.setMarca(rs.getString("marca"));
                carro.setAno(rs.getInt("ano"));
                carro.setCor(rs.getString("cor"));
                carros.add(carro);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar carros: " + e.getMessage(), e);
        }
        
        return carros;
    }

    @Override
    public void atualizar(Carro carro) {
        String sql = "UPDATE carro SET motorista_id = ?, placa = ?, modelo = ?, marca = ?, ano = ?, cor = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, carro.getMotoristaId());
            stmt.setString(2, carro.getPlaca());
            stmt.setString(3, carro.getModelo());
            stmt.setString(4, carro.getMarca());
            stmt.setInt(5, carro.getAno());
            stmt.setString(6, carro.getCor());
            stmt.setInt(7, carro.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar carro: " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM carro WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover carro: " + e.getMessage(), e);
        }
    }

    @Override
    public Carro pesquisar(int id) {
        String sql = "SELECT * FROM carro WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Carro carro = new Carro();
                    carro.setId(rs.getInt("id"));
                    carro.setMotoristaId(rs.getInt("motorista_id"));
                    carro.setPlaca(rs.getString("placa"));
                    carro.setModelo(rs.getString("modelo"));
                    carro.setMarca(rs.getString("marca"));
                    carro.setAno(rs.getInt("ano"));
                    carro.setCor(rs.getString("cor"));
                    return carro;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar carro: " + e.getMessage(), e);
        }
        
        return null;
    }
}


