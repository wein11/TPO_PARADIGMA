package Service;

import java.util.List;

public interface iService<T> {
    T registrar(T t);
    T buscarPorId(int id);
    void eliminarPorId(int id);
    void actualizar(int id);
    List<T> listarTodos();
}
