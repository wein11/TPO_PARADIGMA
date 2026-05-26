package service;

import exception.ClinicaException;
import exception.DatoInvalidoException;
import exception.OdontologoNoEncontradoException;
import exception.PacienteNoEncontradoException;
import exception.TurnoYaReservadoException;
import model.EstadoTurno;
import model.HistorialClinico;
import model.Odontologo;
import model.Paciente;
import model.Turno;
import repository.RepositorioTurno;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TurnoServiceImpl implements iService<Turno> {

    private static final LocalTime HORA_APERTURA = LocalTime.of(8, 0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(20, 0);

    private RepositorioTurno repositorio;
    private PacienteServiceImpl servicioPaciente;
    private OdontologoServiceImpl servicioOdontologo;

    public TurnoServiceImpl(PacienteServiceImpl servicioPaciente, OdontologoServiceImpl servicioOdontologo) {
        this.repositorio = new RepositorioTurno();
        this.servicioPaciente = servicioPaciente;
        this.servicioOdontologo = servicioOdontologo;
    }

    // Constructor para inyectar repositorio ya cargado desde CSV
    public TurnoServiceImpl(PacienteServiceImpl servicioPaciente, OdontologoServiceImpl servicioOdontologo,
                            RepositorioTurno repositorio) {
        this.repositorio = repositorio;
        this.servicioPaciente = servicioPaciente;
        this.servicioOdontologo = servicioOdontologo;
    }

    private void validarDatos(Turno turno) throws DatoInvalidoException {
        if (turno.getPaciente() == null)
            throw new DatoInvalidoException("El paciente indicado no existe en el sistema.");
        if (turno.getOdontologo() == null)
            throw new DatoInvalidoException("El odontólogo indicado no existe en el sistema.");
        if (turno.getFecha() == null)
            throw new DatoInvalidoException("La fecha del turno no puede estar vacía.");
        if (turno.getFecha().isBefore(LocalDate.now()))
            throw new DatoInvalidoException("La fecha del turno no puede ser anterior a hoy.");
        if (turno.getHora() == null)
            throw new DatoInvalidoException("La hora del turno no puede estar vacía.");
        if (turno.getHora().isBefore(HORA_APERTURA) || !turno.getHora().isBefore(HORA_CIERRE))
            throw new DatoInvalidoException("El horario del turno debe estar entre 08:00 y 19:59.");
        if (turno.getHora().getMinute() != 0 && turno.getHora().getMinute() != 30)
            throw new DatoInvalidoException("Los turnos solo pueden darse en horarios cada 30 minutos (ej: 09:00, 09:30).");
    }

    @Override
    public boolean guardar(Turno turno) throws DatoInvalidoException, TurnoYaReservadoException {
        validarDatos(turno);
        verificarDisponibilidadOdontologo(turno, turno.getId());
        repositorio.guardar(turno);
        agregarTurnoAlHistorial(turno);
        return true;
    }

    public boolean registrarTurno(long idPaciente, long idOdontologo, LocalDate fecha, LocalTime hora)
            throws ClinicaException {
        Turno turno = crearTurno(0, idPaciente, idOdontologo, fecha, hora, EstadoTurno.PENDIENTE);
        return guardar(turno);
    }

    @Override
    public Turno buscarPorId(long id) throws ClinicaException {
        Turno turno = repositorio.buscarPorId(id);
        if (turno == null)
            throw new ClinicaException("No se encontró un turno con ID: " + id);
        return turno;
    }

    @Override
    public boolean eliminarPorId(long id) throws ClinicaException {
        Turno turno = repositorio.buscarPorId(id);
        if (turno == null)
            throw new ClinicaException("No se encontró un turno con ID: " + id);
        quitarTurnoDelHistorial(turno);
        repositorio.eliminarPorId(id);
        return true;
    }

    @Override
    public boolean actualizar(Turno turno) throws ClinicaException {
        Turno existente = repositorio.buscarPorId(turno.getId());
        if (existente == null)
            throw new ClinicaException("No se encontró un turno con ID: " + turno.getId());
        validarDatos(turno);
        verificarDisponibilidadOdontologo(turno, turno.getId());
        quitarTurnoDelHistorial(existente);
        repositorio.actualizar(turno);
        agregarTurnoAlHistorial(turno);
        return true;
    }

    public boolean actualizarTurno(long id, long idPaciente, long idOdontologo, LocalDate fecha, LocalTime hora)
            throws ClinicaException {
        Turno existente = repositorio.buscarPorId(id);
        if (existente == null)
            throw new ClinicaException("No se encontró un turno con ID: " + id);
        Turno turno = crearTurno(id, idPaciente, idOdontologo, fecha, hora, existente.getEstado());
        return actualizar(turno);
    }

    @Override
    public List<Turno> listarTodos() {
        return repositorio.listarTodos();
    }

    public boolean confirmarTurno(long id) throws ClinicaException {
        return cambiarEstadoTurno(id, EstadoTurno.CONFIRMADO);
    }

    public boolean cancelarTurno(long id) throws ClinicaException {
        return cambiarEstadoTurno(id, EstadoTurno.CANCELADO);
    }

    public boolean completarTurno(long id) throws ClinicaException {
        return cambiarEstadoTurno(id, EstadoTurno.COMPLETADO);
    }

    // ===================== BÚSQUEDAS AVANZADAS =====================

    public List<Turno> buscarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return repositorio.buscarPorRangoFechas(inicio, fin);
    }

    public List<Turno> filtrarPorOdontologo(long idOdontologo) {
        return repositorio.filtrarPorOdontologo(idOdontologo);
    }

    public List<Turno> filtrarPorPaciente(long idPaciente) {
        return repositorio.filtrarPorPaciente(idPaciente);
    }

    // ===================== PRIVADOS =====================

    private Turno crearTurno(long id, long idPaciente, long idOdontologo, LocalDate fecha, LocalTime hora,
                             EstadoTurno estado)
            throws PacienteNoEncontradoException, OdontologoNoEncontradoException {
        Paciente paciente = servicioPaciente.buscarPorId(idPaciente);
        Odontologo odontologo = servicioOdontologo.buscarPorId(idOdontologo);
        return new Turno(id, paciente, odontologo, fecha, hora, estado);
    }

    private void verificarDisponibilidadOdontologo(Turno turno, long idTurno) throws TurnoYaReservadoException {
        if (turno.getEstado() == EstadoTurno.CANCELADO) return;
        boolean ocupado = repositorio.listarTodos().stream()
                .anyMatch(t -> t.getId() != idTurno
                        && t.getEstado() != EstadoTurno.CANCELADO
                        && t.getOdontologo().getId() == turno.getOdontologo().getId()
                        && t.getFecha().equals(turno.getFecha())
                        && t.getHora().equals(turno.getHora()));
        if (ocupado)
            throw new TurnoYaReservadoException("El odontólogo ya tiene un turno asignado el "
                    + turno.getFecha() + " a las " + turno.getHora() + ".");
    }

    private boolean cambiarEstadoTurno(long id, EstadoTurno nuevoEstado) throws ClinicaException {
        Turno turno = repositorio.buscarPorId(id);
        if (turno == null)
            throw new ClinicaException("No se encontró un turno con ID: " + id);
        if (!estadoPermitido(turno.getEstado(), nuevoEstado))
            throw new ClinicaException("No se puede cambiar el estado de " + turno.getEstado() + " a " + nuevoEstado + ".");
        turno.setEstado(nuevoEstado);
        repositorio.actualizar(turno);
        actualizarTurnoEnHistorial(turno);
        return true;
    }

    private boolean estadoPermitido(EstadoTurno estadoActual, EstadoTurno nuevoEstado) {
        if (estadoActual == EstadoTurno.CANCELADO || estadoActual == EstadoTurno.COMPLETADO) return false;
        if (nuevoEstado == EstadoTurno.CONFIRMADO) return estadoActual == EstadoTurno.PENDIENTE;
        if (nuevoEstado == EstadoTurno.CANCELADO)
            return estadoActual == EstadoTurno.PENDIENTE || estadoActual == EstadoTurno.CONFIRMADO;
        if (nuevoEstado == EstadoTurno.COMPLETADO) return estadoActual == EstadoTurno.CONFIRMADO;
        return false;
    }

    private void agregarTurnoAlHistorial(Turno turno) {
        try {
            HistorialClinico historial = servicioPaciente.obtenerHistorialClinico(turno.getPaciente().getId());
            historial.actualizarTurno(turno);
        } catch (PacienteNoEncontradoException e) {
            // no puede ocurrir: el paciente fue validado antes de llegar aquí
        }
    }

    private void quitarTurnoDelHistorial(Turno turno) {
        try {
            HistorialClinico historial = servicioPaciente.obtenerHistorialClinico(turno.getPaciente().getId());
            historial.eliminarTurnoPorId(turno.getId());
        } catch (PacienteNoEncontradoException e) {
            // no puede ocurrir: el turno tiene paciente válido
        }
    }

    private void actualizarTurnoEnHistorial(Turno turno) {
        try {
            HistorialClinico historial = servicioPaciente.obtenerHistorialClinico(turno.getPaciente().getId());
            historial.actualizarTurno(turno);
        } catch (PacienteNoEncontradoException e) {
            // no puede ocurrir: el paciente fue validado antes de llegar aquí
        }
    }
}
