package service;

import model.Carro;
import model.Motorista;
import repository.IRepository;
import java.util.List;

public class CarroService {
    private IRepository<Carro> repository;
    private IRepository<Motorista> motoristaRepository;

    public CarroService(IRepository<Carro> repository, IRepository<Motorista> motoristaRepository) {
        this.repository = repository;
        this.motoristaRepository = motoristaRepository;
    }

    public void cadastrar(Carro carro) {
        try {
            // Validar se motorista existe antes de cadastrar um carro
            Motorista motorista = motoristaRepository.pesquisar(carro.getMotoristaId());
            if (motorista == null) {
                throw new IllegalArgumentException("Motorista não encontrado. Não é possível cadastrar carro sem motorista válido.");
            }

            if (carro.getPlaca() == null || carro.getPlaca().trim().isEmpty()) {
                throw new IllegalArgumentException("Placa do carro é obrigatória");
            }
            if (carro.getModelo() == null || carro.getModelo().trim().isEmpty()) {
                throw new IllegalArgumentException("Modelo do carro é obrigatório");
            }
            if (carro.getMarca() == null || carro.getMarca().trim().isEmpty()) {
                throw new IllegalArgumentException("Marca do carro é obrigatória");
            }
            if (carro.getAno() <= 0) {
                throw new IllegalArgumentException("Ano do carro inválido");
            }

            repository.cadastrar(carro);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar carro: " + e.getMessage(), e);
        }
    }

    public List<Carro> listar() {
        try {
            return repository.listar();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar carros: " + e.getMessage(), e);
        }
    }

    public void atualizar(Carro carro) {
        try {
            if (carro.getId() <= 0) {
                throw new IllegalArgumentException("ID do carro inválido");
            }
            if (pesquisar(carro.getId()) == null) {
                throw new IllegalArgumentException("Carro não encontrado");
            }

            // Validar se motorista existe
            Motorista motorista = motoristaRepository.pesquisar(carro.getMotoristaId());
            if (motorista == null) {
                throw new IllegalArgumentException("Motorista não encontrado");
            }

            repository.atualizar(carro);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar carro: " + e.getMessage(), e);
        }
    }

    public void remover(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }
            if (pesquisar(id) == null) {
                throw new IllegalArgumentException("Carro não encontrado");
            }
            repository.remover(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover carro: " + e.getMessage(), e);
        }
    }

    public Carro pesquisar(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }
            return repository.pesquisar(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao pesquisar carro: " + e.getMessage(), e);
        }
    }
}


