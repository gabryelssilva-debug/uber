package repository;

import database.ConnectionFactory;
import model.Passageiro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassageiroRepositoryDB implements IRepository<Passageiro> {

    @Override
    public void cadastrar(Passageiro passageiro) {
        String sql = "INSERT INTO passageiro (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, passageiro.getNome());
            stmt.setString(2, passageiro.getCpf());
            stmt.setString(3, passageiro.getTelefone());
            stmt.setString(4, passageiro.getEmail());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    passageiro.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar passageiro: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Passageiro> listar() {
        String sql = "SELECT * FROM passageiro";
        List<Passageiro> passageiros = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Passageiro passageiro = new Passageiro();
                passageiro.setId(rs.getInt("id"));
                passageiro.setNome(rs.getString("nome"));
                passageiro.setCpf(rs.getString("cpf"));
                passageiro.setTelefone(rs.getString("telefone"));
                passageiro.setEmail(rs.getString("email"));
                passageiros.add(passageiro);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar passageiros: " + e.getMessage(), e);
        }
        
        return passageiros;
    }

    @Override
    public void atualizar(Passageiro passageiro) {
        String sql = "UPDATE passageiro SET nome = ?, cpf = ?, telefone = ?, email = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, passageiro.getNome());
            stmt.setString(2, passageiro.getCpf());
            stmt.setString(3, passageiro.getTelefone());
            stmt.setString(4, passageiro.getEmail());
            stmt.setInt(5, passageiro.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar passageiro: " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM passageiro WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover passageiro: " + e.getMessage(), e);
        }
    }

    @Override
    public Passageiro pesquisar(int id) {
        String sql = "SELECT * FROM passageiro WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Passageiro passageiro = new Passageiro();
                    passageiro.setId(rs.getInt("id"));
                    passageiro.setNome(rs.getString("nome"));
                    passageiro.setCpf(rs.getString("cpf"));
                    passageiro.setTelefone(rs.getString("telefone"));
                    passageiro.setEmail(rs.getString("email"));
                    return passageiro;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar passageiro: " + e.getMessage(), e);
        }
        
        return null;
    }
}


