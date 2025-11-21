package service;

import model.Passageiro;
import repository.IRepository;
import java.util.List;

public class PassageiroService {
    private IRepository<Passageiro> repository;

    public PassageiroService(IRepository<Passageiro> repository) {
        this.repository = repository;
    }

    public void cadastrar(Passageiro passageiro) {
        try {
            if (passageiro.getNome() == null || passageiro.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome do passageiro é obrigatório");
            }
            if (passageiro.getCpf() == null || passageiro.getCpf().trim().isEmpty()) {
                throw new IllegalArgumentException("CPF do passageiro é obrigatório");
            }
            repository.cadastrar(passageiro);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar passageiro: " + e.getMessage(), e);
        }
    }

    public List<Passageiro> listar() {
        try {
            return repository.listar();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar passageiros: " + e.getMessage(), e);
        }
    }

    public void atualizar(Passageiro passageiro) {
        try {
            if (passageiro.getId() <= 0) {
                throw new IllegalArgumentException("ID do passageiro inválido");
            }
            if (pesquisar(passageiro.getId()) == null) {
                throw new IllegalArgumentException("Passageiro não encontrado");
            }
            repository.atualizar(passageiro);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar passageiro: " + e.getMessage(), e);
        }
    }

    public void remover(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }
            if (pesquisar(id) == null) {
                throw new IllegalArgumentException("Passageiro não encontrado");
            }
            repository.remover(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover passageiro: " + e.getMessage(), e);
        }
    }

    public Passageiro pesquisar(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }
            return repository.pesquisar(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao pesquisar passageiro: " + e.getMessage(), e);
        }
    }
}


