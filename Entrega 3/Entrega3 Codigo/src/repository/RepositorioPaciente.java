package repository;

import model.Paciente;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioPaciente implements iRepository<Paciente> {

    private List<Paciente> pacientes;
    private long contadorId;

    public RepositorioPaciente() {
        this.pacientes = new ArrayList<>();
        this.contadorId = 1;
    }

    @Override
    public void guardar(Paciente paciente) {
        paciente.setId(contadorId++);
        pacientes.add(paciente);
    }

    @Override
    public Paciente buscarPorId(long id) {
        for (Paciente p : pacientes) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    @Override
    public void eliminarPorId(long id) {
        pacientes.removeIf(p -> p.getId() == id);
    }

    @Override
    public void actualizar(Paciente paciente) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId() == paciente.getId()) {
                pacientes.set(i, paciente);
                return;
            }
        }
    }

    @Override
    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes);
    }

    // Carga desde CSV: agrega sin modificar el ID y ajusta el contador
    public void cargarEntidad(Paciente paciente) {
        pacientes.add(paciente);
        if (paciente.getId() >= contadorId) contadorId = paciente.getId() + 1;
    }

    // Búsqueda por DNI usando Stream
    public Paciente buscarPorDni(Integer dni) {
        return pacientes.stream()
                .filter(p -> p.getDni() != null && p.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }

    // Listado ordenado alfabéticamente por apellido (usa Comparable de Paciente)
    public List<Paciente> listarOrdenadosPorApellido() {
        return pacientes.stream()
                .sorted()
                .collect(Collectors.toList());
    }

}
