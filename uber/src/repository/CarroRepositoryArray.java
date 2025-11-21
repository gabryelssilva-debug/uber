package repository;

import model.Carro;
import java.util.ArrayList;
import java.util.List;

public class CarroRepositoryArray implements IRepository<Carro> {
    private List<Carro> carros;
    private int proximoId;

    public CarroRepositoryArray() {
        this.carros = new ArrayList<>();
        this.proximoId = 1;
    }

    @Override
    public void cadastrar(Carro carro) {
        carro.setId(proximoId++);
        carros.add(carro);
    }

    @Override
    public List<Carro> listar() {
        return new ArrayList<>(carros);
    }

    @Override
    public void atualizar(Carro carro) {
        for (int i = 0; i < carros.size(); i++) {
            if (carros.get(i).getId() == carro.getId()) {
                carros.set(i, carro);
                return;
            }
        }
    }

    @Override
    public void remover(int id) {
        carros.removeIf(c -> c.getId() == id);
    }

    @Override
    public Carro pesquisar(int id) {
        return carros.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}


