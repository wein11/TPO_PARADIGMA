package service;

import exception.DatoInvalidoException;
import exception.OdontologoNoEncontradoException;
import model.Odontologo;
import model.OdontologoEndodoncista;
import model.OdontologoOrtodoncista;
import repository.RepositorioOdontologo;
import java.util.List;

public class OdontologoServiceImpl implements iService<Odontologo> {

    private static final String SOLO_LETRAS = "[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+";
    private static final String MATRICULA_VALIDA = "[A-Za-z0-9-]{3,20}";

    private RepositorioOdontologo repositorio;

    public OdontologoServiceImpl() {
        this.repositorio = new RepositorioOdontologo();
    }

    // Constructor para inyectar repositorio ya cargado desde CSV
    public OdontologoServiceImpl(RepositorioOdontologo repositorio) {
        this.repositorio = repositorio;
    }

    private void validarDatos(Odontologo odontologo) throws DatoInvalidoException {
        if (odontologo.getNombre() == null || odontologo.getNombre().trim().isEmpty())
            throw new DatoInvalidoException("El nombre no puede estar vacío.");
        if (!odontologo.getNombre().trim().matches(SOLO_LETRAS))
            throw new DatoInvalidoException("El nombre solo puede contener letras.");
        if (odontologo.getApellido() == null || odontologo.getApellido().trim().isEmpty())
            throw new DatoInvalidoException("El apellido no puede estar vacío.");
        if (!odontologo.getApellido().trim().matches(SOLO_LETRAS))
            throw new DatoInvalidoException("El apellido solo puede contener letras.");
        if (odontologo.getMatricula() == null || odontologo.getMatricula().trim().isEmpty())
            throw new DatoInvalidoException("La matrícula no puede estar vacía.");
        if (!odontologo.getMatricula().trim().matches(MATRICULA_VALIDA))
            throw new DatoInvalidoException("La matrícula debe tener entre 3 y 20 caracteres alfanuméricos o guiones.");
    }

    @Override
    public boolean guardar(Odontologo odontologo) throws DatoInvalidoException {
        validarDatos(odontologo);
        if (existeMatriculaEnOtroOdontologo(odontologo.getMatricula(), odontologo.getId()))
            throw new DatoInvalidoException("Ya existe un odontólogo registrado con esa matrícula.");
        repositorio.guardar(odontologo);
        return true;
    }

    public boolean registrarOdontologo(String nombre, String apellido, String matricula, int tipoEspecialista)
            throws DatoInvalidoException {
        Odontologo odontologo = crearOdontologo(0, nombre, apellido, matricula, tipoEspecialista);
        return guardar(odontologo);
    }

    @Override
    public Odontologo buscarPorId(long id) throws OdontologoNoEncontradoException {
        Odontologo odontologo = repositorio.buscarPorId(id);
        if (odontologo == null)
            throw new OdontologoNoEncontradoException("No se encontró un odontólogo con ID: " + id);
        return odontologo;
    }

    public Odontologo buscarPorMatricula(String matricula) throws OdontologoNoEncontradoException {
        Odontologo odontologo = repositorio.buscarPorMatricula(matricula);
        if (odontologo == null)
            throw new OdontologoNoEncontradoException("No se encontró un odontólogo con matrícula: " + matricula);
        return odontologo;
    }

    @Override
    public boolean eliminarPorId(long id) throws OdontologoNoEncontradoException {
        if (repositorio.buscarPorId(id) == null)
            throw new OdontologoNoEncontradoException("No se encontró un odontólogo con ID: " + id);
        repositorio.eliminarPorId(id);
        return true;
    }

    @Override
    public boolean actualizar(Odontologo odontologo) throws OdontologoNoEncontradoException, DatoInvalidoException {
        if (repositorio.buscarPorId(odontologo.getId()) == null)
            throw new OdontologoNoEncontradoException("No se encontró un odontólogo con ID: " + odontologo.getId());
        validarDatos(odontologo);
        if (existeMatriculaEnOtroOdontologo(odontologo.getMatricula(), odontologo.getId()))
            throw new DatoInvalidoException("Ya existe un odontólogo registrado con esa matrícula.");
        repositorio.actualizar(odontologo);
        return true;
    }

    public boolean actualizarOdontologo(long id, String nombre, String apellido, String matricula, int tipoEspecialista)
            throws OdontologoNoEncontradoException, DatoInvalidoException {
        Odontologo odontologo = crearOdontologo(id, nombre, apellido, matricula, tipoEspecialista);
        return actualizar(odontologo);
    }

    @Override
    public List<Odontologo> listarTodos() {
        return repositorio.listarTodos();
    }

    private Odontologo crearOdontologo(long id, String nombre, String apellido, String matricula, int tipoEspecialista)
            throws DatoInvalidoException {
        if (tipoEspecialista == 1) return new OdontologoOrtodoncista(id, nombre, apellido, matricula);
        if (tipoEspecialista == 2) return new OdontologoEndodoncista(id, nombre, apellido, matricula);
        throw new DatoInvalidoException("Opción de especialista inválida. Ingrese 1 (Ortodoncista) o 2 (Endodoncista).");
    }

    private boolean existeMatriculaEnOtroOdontologo(String matricula, long idOdontologo) {
        for (Odontologo existente : repositorio.listarTodos()) {
            if (existente.getId() != idOdontologo && existente.getMatricula().equals(matricula))
                return true;
        }
        return false;
    }
}
