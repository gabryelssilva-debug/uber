package repository;

import model.Motorista;
import java.util.ArrayList;
import java.util.List;

public class MotoristaRepositoryArray implements IRepository<Motorista> {
    private List<Motorista> motoristas;
    private int proximoId;

    public MotoristaRepositoryArray() {
        this.motoristas = new ArrayList<>();
        this.proximoId = 1;
    }

    @Override
    public void cadastrar(Motorista motorista) {
        motorista.setId(proximoId++);
        motoristas.add(motorista);
    }

    @Override
    public List<Motorista> listar() {
        return new ArrayList<>(motoristas);
    }

    @Override
    public void atualizar(Motorista motorista) {
        for (int i = 0; i < motoristas.size(); i++) {
            if (motoristas.get(i).getId() == motorista.getId()) {
                motoristas.set(i, motorista);
                return;
            }
        }
    }

    @Override
    public void remover(int id) {
        motoristas.removeIf(m -> m.getId() == id);
    }

    @Override
    public Motorista pesquisar(int id) {
        return motoristas.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }
}


