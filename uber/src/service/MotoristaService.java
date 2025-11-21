package service;

import model.Motorista;
import repository.IRepository;
import java.util.List;

public class MotoristaService {
    private IRepository<Motorista> repository;

    public MotoristaService(IRepository<Motorista> repository) {
        this.repository = repository;
    }

    public void cadastrar(Motorista motorista) {
        try {
            if (motorista.getNome() == null || motorista.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome do motorista é obrigatório");
            }
            if (motorista.getCpf() == null || motorista.getCpf().trim().isEmpty()) {
                throw new IllegalArgumentException("CPF do motorista é obrigatório");
            }
            repository.cadastrar(motorista);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar motorista: " + e.getMessage(), e);
        }
    }

    public List<Motorista> listar() {
        try {
            return repository.listar();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar motoristas: " + e.getMessage(), e);
        }
    }

    public void atualizar(Motorista motorista) {
        try {
            if (motorista.getId() <= 0) {
                throw new IllegalArgumentException("ID do motorista inválido");
            }
            if (pesquisar(motorista.getId()) == null) {
                throw new IllegalArgumentException("Motorista não encontrado");
            }
            repository.atualizar(motorista);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar motorista: " + e.getMessage(), e);
        }
    }

    public void remover(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }
            if (pesquisar(id) == null) {
                throw new IllegalArgumentException("Motorista não encontrado");
            }
            repository.remover(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover motorista: " + e.getMessage(), e);
        }
    }

    public Motorista pesquisar(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }
            return repository.pesquisar(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao pesquisar motorista: " + e.getMessage(), e);
        }
    }
}


