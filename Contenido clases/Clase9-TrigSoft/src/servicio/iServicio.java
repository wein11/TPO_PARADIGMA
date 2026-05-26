package servicio;

import java.util.List;

public interface iServicio<T> {
    void agregar(T entidad);
    T buscarPorId(int id);
    List<T> listarTodos();
    void actualizar(T entidad);
    void eliminar(int id);
}
