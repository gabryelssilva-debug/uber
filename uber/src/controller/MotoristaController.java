package controller;

import model.Motorista;
import service.MotoristaService;
import java.util.List;

public class MotoristaController {
    private MotoristaService service;

    public MotoristaController(MotoristaService service) {
        this.service = service;
    }

    public void cadastrar(String nome, String cpf, String telefone, String email, String cnh) {
        try {
            Motorista motorista = new Motorista();
            motorista.setNome(nome);
            motorista.setCpf(cpf);
            motorista.setTelefone(telefone);
            motorista.setEmail(email);
            motorista.setCnh(cnh);
            service.cadastrar(motorista);
            System.out.println("Motorista cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public List<Motorista> listar() {
        try {
            return service.listar();
        } catch (Exception e) {
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(int id, String nome, String cpf, String telefone, String email, String cnh) {
        try {
            Motorista motorista = new Motorista();
            motorista.setId(id);
            motorista.setNome(nome);
            motorista.setCpf(cpf);
            motorista.setTelefone(telefone);
            motorista.setEmail(email);
            motorista.setCnh(cnh);
            service.atualizar(motorista);
            System.out.println("Motorista atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void remover(int id) {
        try {
            service.remover(id);
            System.out.println("Motorista removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public Motorista pesquisar(int id) {
        try {
            return service.pesquisar(id);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }
}


