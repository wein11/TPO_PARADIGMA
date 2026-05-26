package Repository;

import java.util.List;

public interface iRepository<T> {
    T guardar(T t);
    T buscarPorId(int id);
    void eliminarPorId(int id);
    void actualizar(int id);
    List<T> listarTodos();

}
