package model;

import java.time.LocalDateTime;

public class Corrida {
    private int id;
    private int motoristaId;
    private int passageiroId;
    private String origem;
    private String destino;
    private double distancia;
    private double valor;
    private LocalDateTime dataHora;
    private String status;

    public Corrida() {
    }

    public Corrida(int id, int motoristaId, int passageiroId, String origem, String destino, 
                   double distancia, double valor, LocalDateTime dataHora, String status) {
        this.id = id;
        this.motoristaId = motoristaId;
        this.passageiroId = passageiroId;
        this.origem = origem;
        this.destino = destino;
        this.distancia = distancia;
        this.valor = valor;
        this.dataHora = dataHora;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMotoristaId() {
        return motoristaId;
    }

    public void setMotoristaId(int motoristaId) {
        this.motoristaId = motoristaId;
    }

    public int getPassageiroId() {
        return passageiroId;
    }

    public void setPassageiroId(int passageiroId) {
        this.passageiroId = passageiroId;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Corrida{" +
                "id=" + id +
                ", motoristaId=" + motoristaId +
                ", passageiroId=" + passageiroId +
                ", origem='" + origem + '\'' +
                ", destino='" + destino + '\'' +
                ", distancia=" + distancia +
                ", valor=" + valor +
                ", dataHora=" + dataHora +
                ", status='" + status + '\'' +
                '}';
    }
}


