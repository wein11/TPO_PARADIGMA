package Service;

import Entity.*;
import Repository.EmpleadoRepository;
import java.util.List;

public class EmpleadoServiceImpl implements iService<Empleado> {
    private EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl() {
        this.empleadoRepository = new EmpleadoRepository();
    }

    @Override
    public Empleado registrar(Empleado empleado) {
        if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty()) {
            System.out.println("Error: El nombre es obligatorio");
            return null;
        }

        if (empleado.getSalarioBase() <= 0) {
            System.out.println("Error: El salario debe ser mayor a 0");
            return null;
        }

        Empleado existente = empleadoRepository.buscarPorId(empleado.getId());
        if (existente != null) {
            System.out.println("Error: Ya existe un empleado con ID: " + empleado.getId());
            return null;
        }

        return empleadoRepository.guardar(empleado);
    }

    @Override
    public Empleado buscarPorId(int id) {
        return empleadoRepository.buscarPorId(id);
    }

    @Override
    public void eliminarPorId(int id) {
        empleadoRepository.eliminarPorId(id);
    }

    @Override
    public void actualizar(int id) {
        System.out.println("Metodo actualizar no implementado aun");
    }

    @Override
    public List<Empleado> listarTodos() {
        return empleadoRepository.listarTodos();
    }
}