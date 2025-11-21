package service;

import model.Corrida;
import model.Motorista;
import model.Passageiro;
import repository.IRepository;
import java.util.List;

public class CorridaService {
    private IRepository<Corrida> repository;
    private IRepository<Motorista> motoristaRepository;
    private IRepository<Passageiro> passageiroRepository;

    public CorridaService(IRepository<Corrida> repository, 
                         IRepository<Motorista> motoristaRepository,
                         IRepository<Passageiro> passageiroRepository) {
        this.repository = repository;
        this.motoristaRepository = motoristaRepository;
        this.passageiroRepository = passageiroRepository;
    }

    public void cadastrar(Corrida corrida) {
        try {
            // Validar se motorista e passageiro existem antes de criar corrida
            Motorista motorista = motoristaRepository.pesquisar(corrida.getMotoristaId());
            if (motorista == null) {
                throw new IllegalArgumentException("Motorista não encontrado. Não é possível criar corrida sem motorista válido.");
            }

            Passageiro passageiro = passageiroRepository.pesquisar(corrida.getPassageiroId());
            if (passageiro == null) {
                throw new IllegalArgumentException("Passageiro não encontrado. Não é possível criar corrida sem passageiro válido.");
            }

            if (corrida.getOrigem() == null || corrida.getOrigem().trim().isEmpty()) {
                throw new IllegalArgumentException("Origem da corrida é obrigatória");
            }
            if (corrida.getDestino() == null || corrida.getDestino().trim().isEmpty()) {
                throw new IllegalArgumentException("Destino da corrida é obrigatório");
            }
            if (corrida.getDistancia() <= 0) {
                throw new IllegalArgumentException("Distância da corrida deve ser maior que zero");
            }
            if (corrida.getValor() <= 0) {
                throw new IllegalArgumentException("Valor da corrida deve ser maior que zero");
            }
            if (corrida.getStatus() == null || corrida.getStatus().trim().isEmpty()) {
                throw new IllegalArgumentException("Status da corrida é obrigatório");
            }

            repository.cadastrar(corrida);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar corrida: " + e.getMessage(), e);
        }
    }

    public List<Corrida> listar() {
        try {
            return repository.listar();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar corridas: " + e.getMessage(), e);
        }
    }

    public void atualizar(Corrida corrida) {
        try {
            if (corrida.getId() <= 0) {
                throw new IllegalArgumentException("ID da corrida inválido");
            }
            if (pesquisar(corrida.getId()) == null) {
                throw new IllegalArgumentException("Corrida não encontrada");
            }

            // Validar se motorista e passageiro existem
            Motorista motorista = motoristaRepository.pesquisar(corrida.getMotoristaId());
            if (motorista == null) {
                throw new IllegalArgumentException("Motorista não encontrado");
            }

            Passageiro passageiro = passageiroRepository.pesquisar(corrida.getPassageiroId());
            if (passageiro == null) {
                throw new IllegalArgumentException("Passageiro não encontrado");
            }

            repository.atualizar(corrida);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar corrida: " + e.getMessage(), e);
        }
    }

    public void remover(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }
            if (pesquisar(id) == null) {
                throw new IllegalArgumentException("Corrida não encontrada");
            }
            repository.remover(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover corrida: " + e.getMessage(), e);
        }
    }

    public Corrida pesquisar(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }
            return repository.pesquisar(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao pesquisar corrida: " + e.getMessage(), e);
        }
    }
}


