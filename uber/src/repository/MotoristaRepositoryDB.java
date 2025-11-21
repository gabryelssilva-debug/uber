package repository;

import database.ConnectionFactory;
import model.Motorista;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotoristaRepositoryDB implements IRepository<Motorista> {

    @Override
    public void cadastrar(Motorista motorista) {
        String sql = "INSERT INTO motorista (nome, cpf, telefone, email, cnh) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCpf());
            stmt.setString(3, motorista.getTelefone());
            stmt.setString(4, motorista.getEmail());
            stmt.setString(5, motorista.getCnh());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    motorista.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar motorista: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Motorista> listar() {
        String sql = "SELECT * FROM motorista";
        List<Motorista> motoristas = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Motorista motorista = new Motorista();
                motorista.setId(rs.getInt("id"));
                motorista.setNome(rs.getString("nome"));
                motorista.setCpf(rs.getString("cpf"));
                motorista.setTelefone(rs.getString("telefone"));
                motorista.setEmail(rs.getString("email"));
                motorista.setCnh(rs.getString("cnh"));
                motoristas.add(motorista);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar motoristas: " + e.getMessage(), e);
        }
        
        return motoristas;
    }

    @Override
    public void atualizar(Motorista motorista) {
        String sql = "UPDATE motorista SET nome = ?, cpf = ?, telefone = ?, email = ?, cnh = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCpf());
            stmt.setString(3, motorista.getTelefone());
            stmt.setString(4, motorista.getEmail());
            stmt.setString(5, motorista.getCnh());
            stmt.setInt(6, motorista.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar motorista: " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM motorista WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover motorista: " + e.getMessage(), e);
        }
    }

    @Override
    public Motorista pesquisar(int id) {
        String sql = "SELECT * FROM motorista WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Motorista motorista = new Motorista();
                    motorista.setId(rs.getInt("id"));
                    motorista.setNome(rs.getString("nome"));
                    motorista.setCpf(rs.getString("cpf"));
                    motorista.setTelefone(rs.getString("telefone"));
                    motorista.setEmail(rs.getString("email"));
                    motorista.setCnh(rs.getString("cnh"));
                    return motorista;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar motorista: " + e.getMessage(), e);
        }
        
        return null;
    }
}


