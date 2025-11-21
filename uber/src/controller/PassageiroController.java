package controller;

import model.Passageiro;
import service.PassageiroService;
import java.util.List;

public class PassageiroController {
    private PassageiroService service;

    public PassageiroController(PassageiroService service) {
        this.service = service;
    }

    public void cadastrar(String nome, String cpf, String telefone, String email) {
        try {
            Passageiro passageiro = new Passageiro();
            passageiro.setNome(nome);
            passageiro.setCpf(cpf);
            passageiro.setTelefone(telefone);
            passageiro.setEmail(email);
            service.cadastrar(passageiro);
            System.out.println("Passageiro cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public List<Passageiro> listar() {
        try {
            return service.listar();
        } catch (Exception e) {
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(int id, String nome, String cpf, String telefone, String email) {
        try {
            Passageiro passageiro = new Passageiro();
            passageiro.setId(id);
            passageiro.setNome(nome);
            passageiro.setCpf(cpf);
            passageiro.setTelefone(telefone);
            passageiro.setEmail(email);
            service.atualizar(passageiro);
            System.out.println("Passageiro atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void remover(int id) {
        try {
            service.remover(id);
            System.out.println("Passageiro removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public Passageiro pesquisar(int id) {
        try {
            return service.pesquisar(id);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }
}


