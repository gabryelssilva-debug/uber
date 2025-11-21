package controller;

import model.Carro;
import service.CarroService;
import java.util.List;

public class CarroController {
    private CarroService service;

    public CarroController(CarroService service) {
        this.service = service;
    }

    public void cadastrar(int motoristaId, String placa, String modelo, String marca, int ano, String cor) {
        try {
            Carro carro = new Carro();
            carro.setMotoristaId(motoristaId);
            carro.setPlaca(placa);
            carro.setModelo(modelo);
            carro.setMarca(marca);
            carro.setAno(ano);
            carro.setCor(cor);
            service.cadastrar(carro);
            System.out.println("Carro cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public List<Carro> listar() {
        try {
            return service.listar();
        } catch (Exception e) {
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(int id, int motoristaId, String placa, String modelo, String marca, int ano, String cor) {
        try {
            Carro carro = new Carro();
            carro.setId(id);
            carro.setMotoristaId(motoristaId);
            carro.setPlaca(placa);
            carro.setModelo(modelo);
            carro.setMarca(marca);
            carro.setAno(ano);
            carro.setCor(cor);
            service.atualizar(carro);
            System.out.println("Carro atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void remover(int id) {
        try {
            service.remover(id);
            System.out.println("Carro removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public Carro pesquisar(int id) {
        try {
            return service.pesquisar(id);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }
}


