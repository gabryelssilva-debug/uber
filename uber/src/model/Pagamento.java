package model;

import java.time.LocalDateTime;

public class Pagamento {
    private int id;
    private int corridaId;
    private double valor;
    private String metodoPagamento;
    private LocalDateTime dataPagamento;
    private String status;

    public Pagamento() {
    }

    public Pagamento(int id, int corridaId, double valor, String metodoPagamento, 
                     LocalDateTime dataPagamento, String status) {
        this.id = id;
        this.corridaId = corridaId;
        this.valor = valor;
        this.metodoPagamento = metodoPagamento;
        this.dataPagamento = dataPagamento;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCorridaId() {
        return corridaId;
    }

    public void setCorridaId(int corridaId) {
        this.corridaId = corridaId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "id=" + id +
                ", corridaId=" + corridaId +
                ", valor=" + valor +
                ", metodoPagamento='" + metodoPagamento + '\'' +
                ", dataPagamento=" + dataPagamento +
                ", status='" + status + '\'' +
                '}';
    }
}


