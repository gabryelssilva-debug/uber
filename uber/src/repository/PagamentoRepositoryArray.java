package repository;

import model.Pagamento;
import java.util.ArrayList;
import java.util.List;

public class PagamentoRepositoryArray implements IRepository<Pagamento> {
    private List<Pagamento> pagamentos;
    private int proximoId;

    public PagamentoRepositoryArray() {
        this.pagamentos = new ArrayList<>();
        this.proximoId = 1;
    }

    @Override
    public void cadastrar(Pagamento pagamento) {
        pagamento.setId(proximoId++);
        pagamentos.add(pagamento);
    }

    @Override
    public List<Pagamento> listar() {
        return new ArrayList<>(pagamentos);
    }

    @Override
    public void atualizar(Pagamento pagamento) {
        for (int i = 0; i < pagamentos.size(); i++) {
            if (pagamentos.get(i).getId() == pagamento.getId()) {
                pagamentos.set(i, pagamento);
                return;
            }
        }
    }

    @Override
    public void remover(int id) {
        pagamentos.removeIf(p -> p.getId() == id);
    }

    @Override
    public Pagamento pesquisar(int id) {
        return pagamentos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}


