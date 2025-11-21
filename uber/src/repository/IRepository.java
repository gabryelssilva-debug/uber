package repository;

import java.util.List;

public interface IRepository<T> {
    void cadastrar(T entidade);
    List<T> listar();
    void atualizar(T entidade);
    void remover(int id);
    T pesquisar(int id);
}


