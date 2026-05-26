package service;

import exception.DatoInvalidoException;
import exception.PacienteNoEncontradoException;
import model.Domicilio;
import model.HistorialClinico;
import model.Paciente;
import repository.RepositorioPaciente;
import java.time.LocalDate;
import java.util.List;

public class PacienteServiceImpl implements iService<Paciente> {

    private static final String SOLO_LETRAS = "[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+";
    private static final String EMAIL_VALIDO = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private RepositorioPaciente repositorio;

    public PacienteServiceImpl() {
        this.repositorio = new RepositorioPaciente();
    }

    // Constructor para inyectar repositorio ya cargado desde CSV
    public PacienteServiceImpl(RepositorioPaciente repositorio) {
        this.repositorio = repositorio;
    }

    private void validarDatos(Paciente paciente) throws DatoInvalidoException {
        if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty())
            throw new DatoInvalidoException("El nombre no puede estar vacío.");
        if (!paciente.getNombre().trim().matches(SOLO_LETRAS))
            throw new DatoInvalidoException("El nombre solo puede contener letras.");
        if (paciente.getApellido() == null || paciente.getApellido().trim().isEmpty())
            throw new DatoInvalidoException("El apellido no puede estar vacío.");
        if (!paciente.getApellido().trim().matches(SOLO_LETRAS))
            throw new DatoInvalidoException("El apellido solo puede contener letras.");
        if (paciente.getDni() == null || paciente.getDni() < 1000000 || paciente.getDni() > 99999999)
            throw new DatoInvalidoException("El DNI debe tener entre 7 y 8 dígitos.");
        if (paciente.getEmail() == null || paciente.getEmail().trim().isEmpty() || !paciente.getEmail().trim().matches(EMAIL_VALIDO))
            throw new DatoInvalidoException("El email debe tener un formato válido.");
        if (paciente.getFechaIngreso() == null)
            throw new DatoInvalidoException("La fecha de ingreso no puede estar vacía.");
        if (paciente.getFechaIngreso().isAfter(LocalDate.now()))
            throw new DatoInvalidoException("La fecha de ingreso no puede ser futura.");
        if (paciente.getDomicilio() == null)
            throw new DatoInvalidoException("El domicilio no puede estar vacío.");
        if (paciente.getDomicilio().getCalle() == null || paciente.getDomicilio().getCalle().trim().isEmpty())
            throw new DatoInvalidoException("La calle no puede estar vacía.");
        if (paciente.getDomicilio().getNumero() <= 0)
            throw new DatoInvalidoException("El número de calle debe ser mayor a 0.");
        if (paciente.getDomicilio().getLocalidad() == null || paciente.getDomicilio().getLocalidad().trim().isEmpty())
            throw new DatoInvalidoException("La localidad no puede estar vacía.");
        if (!paciente.getDomicilio().getLocalidad().trim().matches(SOLO_LETRAS))
            throw new DatoInvalidoException("La localidad solo puede contener letras.");
        if (paciente.getDomicilio().getProvincia() == null || paciente.getDomicilio().getProvincia().trim().isEmpty())
            throw new DatoInvalidoException("La provincia no puede estar vacía.");
        if (!paciente.getDomicilio().getProvincia().trim().matches(SOLO_LETRAS))
            throw new DatoInvalidoException("La provincia solo puede contener letras.");
    }

    @Override
    public boolean guardar(Paciente paciente) throws DatoInvalidoException {
        validarDatos(paciente);
        if (existeDniEnOtroPaciente(paciente.getDni(), paciente.getId()))
            throw new DatoInvalidoException("Ya existe un paciente registrado con ese DNI.");
        repositorio.guardar(paciente);
        asegurarHistorialClinico(paciente);
        return true;
    }

    public boolean registrarPaciente(String nombre, String apellido, int dni, String email, LocalDate fechaIngreso,
                                     String calle, int numeroCalle, String localidad, String provincia)
            throws DatoInvalidoException {
        Domicilio domicilio = crearDomicilio(calle, numeroCalle, localidad, provincia);
        Paciente paciente = new Paciente(0, nombre, apellido, dni, email, fechaIngreso, domicilio);
        return guardar(paciente);
    }

    @Override
    public Paciente buscarPorId(long id) throws PacienteNoEncontradoException {
        Paciente paciente = repositorio.buscarPorId(id);
        if (paciente == null)
            throw new PacienteNoEncontradoException("No se encontró un paciente con ID: " + id);
        asegurarHistorialClinico(paciente);
        return paciente;
    }

    public Paciente buscarPorDni(Integer dni) throws PacienteNoEncontradoException {
        Paciente paciente = repositorio.buscarPorDni(dni);
        if (paciente == null)
            throw new PacienteNoEncontradoException("No se encontró un paciente con DNI: " + dni);
        asegurarHistorialClinico(paciente);
        return paciente;
    }

    @Override
    public boolean eliminarPorId(long id) throws PacienteNoEncontradoException {
        if (repositorio.buscarPorId(id) == null)
            throw new PacienteNoEncontradoException("No se encontró un paciente con ID: " + id);
        repositorio.eliminarPorId(id);
        return true;
    }

    @Override
    public boolean actualizar(Paciente paciente) throws PacienteNoEncontradoException, DatoInvalidoException {
        Paciente existente = repositorio.buscarPorId(paciente.getId());
        if (existente == null)
            throw new PacienteNoEncontradoException("No se encontró un paciente con ID: " + paciente.getId());
        validarDatos(paciente);
        if (existeDniEnOtroPaciente(paciente.getDni(), paciente.getId()))
            throw new DatoInvalidoException("Ya existe un paciente registrado con ese DNI.");
        paciente.setHistorialClinico(existente.getHistorialClinico());
        asegurarHistorialClinico(paciente);
        repositorio.actualizar(paciente);
        return true;
    }

    public boolean actualizarPaciente(long id, String nombre, String apellido, int dni, String email,
                                      LocalDate fechaIngreso, String calle, int numeroCalle,
                                      String localidad, String provincia)
            throws PacienteNoEncontradoException, DatoInvalidoException {
        Domicilio domicilio = crearDomicilio(calle, numeroCalle, localidad, provincia);
        Paciente paciente = new Paciente(id, nombre, apellido, dni, email, fechaIngreso, domicilio);
        return actualizar(paciente);
    }

    @Override
    public List<Paciente> listarTodos() {
        for (Paciente paciente : repositorio.listarTodos()) {
            asegurarHistorialClinico(paciente);
        }
        return repositorio.listarTodos();
    }

    public List<Paciente> listarOrdenadosPorApellido() {
        List<Paciente> lista = repositorio.listarOrdenadosPorApellido();
        for (Paciente p : lista) asegurarHistorialClinico(p);
        return lista;
    }

    public HistorialClinico obtenerHistorialClinico(long idPaciente) throws PacienteNoEncontradoException {
        Paciente paciente = repositorio.buscarPorId(idPaciente);
        if (paciente == null)
            throw new PacienteNoEncontradoException("No se encontró un paciente con ID: " + idPaciente);
        asegurarHistorialClinico(paciente);
        return paciente.getHistorialClinico();
    }

    private Domicilio crearDomicilio(String calle, int numeroCalle, String localidad, String provincia) {
        return new Domicilio(calle, numeroCalle, localidad, provincia, "");
    }

    private boolean existeDniEnOtroPaciente(Integer dni, long idPaciente) {
        for (Paciente existente : repositorio.listarTodos()) {
            if (existente.getId() != idPaciente && existente.getDni() != null && existente.getDni().equals(dni))
                return true;
        }
        return false;
    }

    private void asegurarHistorialClinico(Paciente paciente) {
        if (paciente.getHistorialClinico() == null) {
            paciente.setHistorialClinico(new HistorialClinico(paciente.getId(), paciente, "Sin antecedentes registrados"));
        } else {
            paciente.getHistorialClinico().setPaciente(paciente);
        }
    }
}
