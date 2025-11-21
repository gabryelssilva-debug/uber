package repository;

import model.Passageiro;
import java.util.ArrayList;
import java.util.List;

public class PassageiroRepositoryArray implements IRepository<Passageiro> {
    private List<Passageiro> passageiros;
    private int proximoId;

    public PassageiroRepositoryArray() {
        this.passageiros = new ArrayList<>();
        this.proximoId = 1;
    }

    @Override
    public void cadastrar(Passageiro passageiro) {
        passageiro.setId(proximoId++);
        passageiros.add(passageiro);
    }

    @Override
    public List<Passageiro> listar() {
        return new ArrayList<>(passageiros);
    }

    @Override
    public void atualizar(Passageiro passageiro) {
        for (int i = 0; i < passageiros.size(); i++) {
            if (passageiros.get(i).getId() == passageiro.getId()) {
                passageiros.set(i, passageiro);
                return;
            }
        }
    }

    @Override
    public void remover(int id) {
        passageiros.removeIf(p -> p.getId() == id);
    }

    @Override
    public Passageiro pesquisar(int id) {
        return passageiros.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}


