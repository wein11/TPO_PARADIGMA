package service;

import exception.ClinicaException;
import java.util.List;

public interface iService<T> {
    boolean guardar(T t) throws ClinicaException;
    T buscarPorId(long id) throws ClinicaException;
    boolean eliminarPorId(long id) throws ClinicaException;
    boolean actualizar(T t) throws ClinicaException;
    List<T> listarTodos();
}
