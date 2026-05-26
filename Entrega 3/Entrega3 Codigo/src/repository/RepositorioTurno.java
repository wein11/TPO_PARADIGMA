package repository;

import model.Turno;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioTurno implements iRepository<Turno> {

    private List<Turno> turnos;
    private long contadorId;

    public RepositorioTurno() {
        this.turnos = new ArrayList<>();
        this.contadorId = 1;
    }

    @Override
    public void guardar(Turno turno) {
        turno.setId(contadorId++);
        turnos.add(turno);
    }

    @Override
    public Turno buscarPorId(long id) {
        for (Turno t : turnos) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    @Override
    public void eliminarPorId(long id) {
        turnos.removeIf(t -> t.getId() == id);
    }

    @Override
    public void actualizar(Turno turno) {
        for (int i = 0; i < turnos.size(); i++) {
            if (turnos.get(i).getId() == turno.getId()) {
                turnos.set(i, turno);
                return;
            }
        }
    }

    @Override
    public List<Turno> listarTodos() {
        return new ArrayList<>(turnos);
    }

    // Carga desde CSV: agrega sin modificar el ID y ajusta el contador
    public void cargarEntidad(Turno turno) {
        turnos.add(turno);
        if (turno.getId() >= contadorId) contadorId = turno.getId() + 1;
    }

    // Filtro por rango de fechas usando Stream
    public List<Turno> buscarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return turnos.stream()
                .filter(t -> !t.getFecha().isBefore(inicio) && !t.getFecha().isAfter(fin))
                .collect(Collectors.toList());
    }

    // Filtro por odontólogo usando Stream
    public List<Turno> filtrarPorOdontologo(long idOdontologo) {
        return turnos.stream()
                .filter(t -> t.getOdontologo().getId() == idOdontologo)
                .collect(Collectors.toList());
    }

    // Filtro por paciente usando Stream
    public List<Turno> filtrarPorPaciente(long idPaciente) {
        return turnos.stream()
                .filter(t -> t.getPaciente().getId() == idPaciente)
                .collect(Collectors.toList());
    }

    // Verifica si el odontólogo ya tiene turno en esa fecha y hora exactas
    public boolean existeTurnoEnHorario(long idOdontologo, LocalDate fecha, LocalTime hora) {
        return turnos.stream()
                .anyMatch(t -> t.getOdontologo().getId() == idOdontologo
                        && t.getFecha().equals(fecha)
                        && t.getHora().equals(hora));
    }

}
