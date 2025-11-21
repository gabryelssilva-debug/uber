package controller;

import model.Corrida;
import service.CorridaService;
import java.time.LocalDateTime;
import java.util.List;

public class CorridaController {
    private CorridaService service;

    public CorridaController(CorridaService service) {
        this.service = service;
    }

    public void cadastrar(int motoristaId, int passageiroId, String origem, String destino, 
                         double distancia, double valor, LocalDateTime dataHora, String status) {
        try {
            Corrida corrida = new Corrida();
            corrida.setMotoristaId(motoristaId);
            corrida.setPassageiroId(passageiroId);
            corrida.setOrigem(origem);
            corrida.setDestino(destino);
            corrida.setDistancia(distancia);
            corrida.setValor(valor);
            corrida.setDataHora(dataHora);
            corrida.setStatus(status);
            service.cadastrar(corrida);
            System.out.println("Corrida cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public List<Corrida> listar() {
        try {
            return service.listar();
        } catch (Exception e) {
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(int id, int motoristaId, int passageiroId, String origem, String destino, 
                         double distancia, double valor, LocalDateTime dataHora, String status) {
        try {
            Corrida corrida = new Corrida();
            corrida.setId(id);
            corrida.setMotoristaId(motoristaId);
            corrida.setPassageiroId(passageiroId);
            corrida.setOrigem(origem);
            corrida.setDestino(destino);
            corrida.setDistancia(distancia);
            corrida.setValor(valor);
            corrida.setDataHora(dataHora);
            corrida.setStatus(status);
            service.atualizar(corrida);
            System.out.println("Corrida atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void remover(int id) {
        try {
            service.remover(id);
            System.out.println("Corrida removida com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public Corrida pesquisar(int id) {
        try {
            return service.pesquisar(id);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }
}


