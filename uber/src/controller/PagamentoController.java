package controller;

import model.Pagamento;
import service.PagamentoService;
import java.time.LocalDateTime;
import java.util.List;

public class PagamentoController {
    private PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    public void cadastrar(int corridaId, double valor, String metodoPagamento, 
                         LocalDateTime dataPagamento, String status) {
        try {
            Pagamento pagamento = new Pagamento();
            pagamento.setCorridaId(corridaId);
            pagamento.setValor(valor);
            pagamento.setMetodoPagamento(metodoPagamento);
            pagamento.setDataPagamento(dataPagamento);
            pagamento.setStatus(status);
            service.cadastrar(pagamento);
            System.out.println("Pagamento cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public List<Pagamento> listar() {
        try {
            return service.listar();
        } catch (Exception e) {
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(int id, int corridaId, double valor, String metodoPagamento, 
                         LocalDateTime dataPagamento, String status) {
        try {
            Pagamento pagamento = new Pagamento();
            pagamento.setId(id);
            pagamento.setCorridaId(corridaId);
            pagamento.setValor(valor);
            pagamento.setMetodoPagamento(metodoPagamento);
            pagamento.setDataPagamento(dataPagamento);
            pagamento.setStatus(status);
            service.atualizar(pagamento);
            System.out.println("Pagamento atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void remover(int id) {
        try {
            service.remover(id);
            System.out.println("Pagamento removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public Pagamento pesquisar(int id) {
        try {
            return service.pesquisar(id);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }
}


