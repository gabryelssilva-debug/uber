package service;

import model.Corrida;
import model.Pagamento;
import repository.IRepository;
import java.util.List;

public class PagamentoService {
    private IRepository<Pagamento> repository;
    private IRepository<Corrida> corridaRepository;

    public PagamentoService(IRepository<Pagamento> repository, IRepository<Corrida> corridaRepository) {
        this.repository = repository;
        this.corridaRepository = corridaRepository;
    }

    public void cadastrar(Pagamento pagamento) {
        try {
            // Validar se corrida existe
            Corrida corrida = corridaRepository.pesquisar(pagamento.getCorridaId());
            if (corrida == null) {
                throw new IllegalArgumentException("Corrida não encontrada. Não é possível cadastrar pagamento sem corrida válida.");
            }

            // Validar valores de pagamento
            if (pagamento.getValor() <= 0) {
                throw new IllegalArgumentException("Valor do pagamento deve ser maior que zero");
            }

            if (pagamento.getValor() > corrida.getValor()) {
                throw new IllegalArgumentException("Valor do pagamento não pode ser maior que o valor da corrida");
            }

            if (pagamento.getMetodoPagamento() == null || pagamento.getMetodoPagamento().trim().isEmpty()) {
                throw new IllegalArgumentException("Método de pagamento é obrigatório");
            }

            if (pagamento.getStatus() == null || pagamento.getStatus().trim().isEmpty()) {
                throw new IllegalArgumentException("Status do pagamento é obrigatório");
            }

            repository.cadastrar(pagamento);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar pagamento: " + e.getMessage(), e);
        }
    }

    public List<Pagamento> listar() {
        try {
            return repository.listar();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar pagamentos: " + e.getMessage(), e);
        }
    }

    public void atualizar(Pagamento pagamento) {
        try {
            if (pagamento.getId() <= 0) {
                throw new IllegalArgumentException("ID do pagamento inválido");
            }
            if (pesquisar(pagamento.getId()) == null) {
                throw new IllegalArgumentException("Pagamento não encontrado");
            }

            // Validar valores de pagamento
            if (pagamento.getValor() <= 0) {
                throw new IllegalArgumentException("Valor do pagamento deve ser maior que zero");
            }

            Corrida corrida = corridaRepository.pesquisar(pagamento.getCorridaId());
            if (corrida == null) {
                throw new IllegalArgumentException("Corrida não encontrada");
            }

            if (pagamento.getValor() > corrida.getValor()) {
                throw new IllegalArgumentException("Valor do pagamento não pode ser maior que o valor da corrida");
            }

            repository.atualizar(pagamento);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar pagamento: " + e.getMessage(), e);
        }
    }

    public void remover(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }
            if (pesquisar(id) == null) {
                throw new IllegalArgumentException("Pagamento não encontrado");
            }
            repository.remover(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover pagamento: " + e.getMessage(), e);
        }
    }

    public Pagamento pesquisar(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }
            return repository.pesquisar(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao pesquisar pagamento: " + e.getMessage(), e);
        }
    }
}


