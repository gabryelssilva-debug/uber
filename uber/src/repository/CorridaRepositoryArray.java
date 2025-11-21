package repository;

import model.Corrida;
import java.util.ArrayList;
import java.util.List;

public class CorridaRepositoryArray implements IRepository<Corrida> {
    private List<Corrida> corridas;
    private int proximoId;

    public CorridaRepositoryArray() {
        this.corridas = new ArrayList<>();
        this.proximoId = 1;
    }

    @Override
    public void cadastrar(Corrida corrida) {
        corrida.setId(proximoId++);
        corridas.add(corrida);
    }

    @Override
    public List<Corrida> listar() {
        return new ArrayList<>(corridas);
    }

    @Override
    public void atualizar(Corrida corrida) {
        for (int i = 0; i < corridas.size(); i++) {
            if (corridas.get(i).getId() == corrida.getId()) {
                corridas.set(i, corrida);
                return;
            }
        }
    }

    @Override
    public void remover(int id) {
        corridas.removeIf(c -> c.getId() == id);
    }

    @Override
    public Corrida pesquisar(int id) {
        return corridas.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}


