package ui;

import exception.ClinicaException;
import model.HistorialClinico;
import model.Odontologo;
import model.Paciente;
import model.Turno;
import service.OdontologoServiceImpl;
import service.PacienteServiceImpl;
import service.TurnoServiceImpl;
import java.time.LocalDate;
import java.util.List;

public class ClinicaController implements Runnable {

    private VistaClinica vista;
    private PacienteServiceImpl servicioPaciente;
    private OdontologoServiceImpl servicioOdontologo;
    private TurnoServiceImpl servicioTurno;
    private boolean ejecutando;

    public ClinicaController(VistaClinica vista) {
        this.vista = vista;
        this.servicioPaciente = new PacienteServiceImpl();
        this.servicioOdontologo = new OdontologoServiceImpl();
        this.servicioTurno = new TurnoServiceImpl(servicioPaciente, servicioOdontologo);
        this.ejecutando = true;
    }

    // Constructor para inyectar repositorios ya cargados desde CSV
    public ClinicaController(VistaClinica vista, PacienteServiceImpl servicioPaciente,
                             OdontologoServiceImpl servicioOdontologo, TurnoServiceImpl servicioTurno) {
        this.vista = vista;
        this.servicioPaciente = servicioPaciente;
        this.servicioOdontologo = servicioOdontologo;
        this.servicioTurno = servicioTurno;
        this.ejecutando = true;
    }

    @Override
    public void run() {
        while (ejecutando) {
            vista.mostrarMenuInicial();
            int opcion = vista.pedirOpcion();
            switch (opcion) {
                case 1 -> menuPacientes();
                case 2 -> menuOdontologos();
                case 3 -> menuTurnos();
                case 0 -> salir();
                default -> vista.mostrarError("Opcion invalida. Intente nuevamente.");
            }
        }
        vista.cerrar();
    }

    // ===================== PACIENTES =====================

    private void menuPacientes() {
        boolean enSubmenu = true;
        while (enSubmenu) {
            vista.mostrarMenuPacientes();
            int op = vista.pedirOpcion();
            switch (op) {
                case 1 -> registrarPaciente();
                case 2 -> buscarPaciente();
                case 3 -> eliminarPaciente();
                case 4 -> actualizarPaciente();
                case 5 -> listarPacientes();
                case 6 -> mostrarHistorialClinico();
                case 7 -> buscarPacientePorDni();
                case 8 -> listarPacientesPorApellido();
                case 0 -> enSubmenu = false;
                default -> vista.mostrarError("Opcion invalida.");
            }
        }
    }

    private void registrarPaciente() {
        DatoPaciente dato = vista.pedirDatosPaciente();
        if (dato == null) { vista.pausar(); return; }
        try {
            servicioPaciente.registrarPaciente(dato.getNombre(), dato.getApellido(), dato.getDni(),
                    dato.getEmail(), dato.getFechaIngreso(),
                    dato.getCalle(), dato.getNumeroCalle(), dato.getLocalidad(), dato.getProvincia());
            vista.mostrarMensaje("Paciente registrado correctamente.");
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void buscarPaciente() {
        long id = vista.pedirId();
        try {
            Paciente paciente = servicioPaciente.buscarPorId(id);
            vista.mostrarMensaje(paciente.toString());
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void eliminarPaciente() {
        long id = vista.pedirId();
        if (!vista.pedirConfirmacion("Confirma la eliminacion del paciente con ID " + id + "?")) {
            vista.mostrarMensaje("Operacion cancelada.");
            vista.pausar();
            return;
        }
        try {
            servicioPaciente.eliminarPorId(id);
            vista.mostrarMensaje("Paciente eliminado correctamente.");
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void actualizarPaciente() {
        long id = vista.pedirId();
        vista.mostrarMensaje("Ingrese los nuevos datos para el paciente con ID: " + id);
        DatoPaciente dato = vista.pedirDatosPaciente();
        if (dato == null) { vista.pausar(); return; }
        try {
            servicioPaciente.actualizarPaciente(id, dato.getNombre(), dato.getApellido(), dato.getDni(),
                    dato.getEmail(), dato.getFechaIngreso(),
                    dato.getCalle(), dato.getNumeroCalle(), dato.getLocalidad(), dato.getProvincia());
            vista.mostrarMensaje("Paciente actualizado correctamente.");
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void listarPacientes() {
        List<Paciente> pacientes = servicioPaciente.listarTodos();
        if (pacientes.isEmpty()) {
            vista.mostrarMensaje("No hay pacientes registrados.");
        } else {
            System.out.println("\n--- LISTA DE PACIENTES ---");
            for (Paciente p : pacientes) System.out.println(p);
        }
        vista.pausar();
    }

    private void mostrarHistorialClinico() {
        long id = vista.pedirId();
        try {
            HistorialClinico historial = servicioPaciente.obtenerHistorialClinico(id);
            System.out.println("\n--- HISTORIAL CLINICO ---");
            System.out.println(historial);
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void buscarPacientePorDni() {
        int dni = vista.pedirDni();
        try {
            Paciente paciente = servicioPaciente.buscarPorDni(dni);
            vista.mostrarMensaje(paciente.toString());
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void listarPacientesPorApellido() {
        List<Paciente> pacientes = servicioPaciente.listarOrdenadosPorApellido();
        if (pacientes.isEmpty()) {
            vista.mostrarMensaje("No hay pacientes registrados.");
        } else {
            System.out.println("\n--- PACIENTES ORDENADOS POR APELLIDO ---");
            for (Paciente p : pacientes) System.out.println(p);
        }
        vista.pausar();
    }

    // ===================== ODONTOLOGOS =====================

    private void menuOdontologos() {
        boolean enSubmenu = true;
        while (enSubmenu) {
            vista.mostrarMenuOdontologos();
            int op = vista.pedirOpcion();
            switch (op) {
                case 1 -> registrarOdontologo();
                case 2 -> buscarOdontologo();
                case 3 -> eliminarOdontologo();
                case 4 -> actualizarOdontologo();
                case 5 -> listarOdontologos();
                case 0 -> enSubmenu = false;
                default -> vista.mostrarError("Opcion invalida.");
            }
        }
    }

    private void registrarOdontologo() {
        DatoOdontologo dato = vista.pedirDatosOdontologo();
        if (dato == null) { vista.pausar(); return; }
        try {
            servicioOdontologo.registrarOdontologo(dato.getNombre(), dato.getApellido(),
                    dato.getMatricula(), dato.getTipoEspecialista());
            vista.mostrarMensaje("Odontologo registrado correctamente.");
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void buscarOdontologo() {
        long id = vista.pedirId();
        try {
            Odontologo odontologo = servicioOdontologo.buscarPorId(id);
            vista.mostrarMensaje(odontologo.toString());
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void eliminarOdontologo() {
        long id = vista.pedirId();
        if (!vista.pedirConfirmacion("Confirma la eliminacion del odontologo con ID " + id + "?")) {
            vista.mostrarMensaje("Operacion cancelada.");
            vista.pausar();
            return;
        }
        try {
            servicioOdontologo.eliminarPorId(id);
            vista.mostrarMensaje("Odontologo eliminado correctamente.");
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void actualizarOdontologo() {
        long id = vista.pedirId();
        vista.mostrarMensaje("Ingrese los nuevos datos para el odontologo con ID: " + id);
        DatoOdontologo dato = vista.pedirDatosOdontologo();
        if (dato == null) { vista.pausar(); return; }
        try {
            servicioOdontologo.actualizarOdontologo(id, dato.getNombre(), dato.getApellido(),
                    dato.getMatricula(), dato.getTipoEspecialista());
            vista.mostrarMensaje("Odontologo actualizado correctamente.");
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void listarOdontologos() {
        List<Odontologo> odontologos = servicioOdontologo.listarTodos();
        if (odontologos.isEmpty()) {
            vista.mostrarMensaje("No hay odontologos registrados.");
        } else {
            System.out.println("\n--- LISTA DE ODONTOLOGOS ---");
            for (Odontologo o : odontologos) System.out.println(o);
        }
        vista.pausar();
    }

    // ===================== TURNOS =====================

    private void menuTurnos() {
        boolean enSubmenu = true;
        while (enSubmenu) {
            vista.mostrarMenuTurnos();
            int op = vista.pedirOpcion();
            switch (op) {
                case 1  -> registrarTurno();
                case 2  -> buscarTurno();
                case 3  -> eliminarTurno();
                case 4  -> actualizarTurno();
                case 5  -> listarTurnos();
                case 6  -> confirmarTurno();
                case 7  -> cancelarTurno();
                case 8  -> completarTurno();
                case 9  -> buscarTurnosPorRangoFechas();
                case 10 -> filtrarTurnosPorOdontologo();
                case 11 -> filtrarTurnosPorPaciente();
                case 0  -> enSubmenu = false;
                default -> vista.mostrarError("Opcion invalida.");
            }
        }
    }

    private void registrarTurno() {
        DatoTurno dato = vista.pedirDatosTurno();
        if (dato == null) { vista.pausar(); return; }
        try {
            servicioTurno.registrarTurno(dato.getIdPaciente(), dato.getIdOdontologo(),
                    dato.getFecha(), dato.getHora());
            vista.mostrarMensaje("Turno registrado correctamente.");
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void buscarTurno() {
        long id = vista.pedirId();
        try {
            Turno turno = servicioTurno.buscarPorId(id);
            vista.mostrarMensaje(turno.toString());
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void eliminarTurno() {
        long id = vista.pedirId();
        if (!vista.pedirConfirmacion("Confirma la eliminacion del turno con ID " + id + "?")) {
            vista.mostrarMensaje("Operacion cancelada.");
            vista.pausar();
            return;
        }
        try {
            servicioTurno.eliminarPorId(id);
            vista.mostrarMensaje("Turno eliminado correctamente.");
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void actualizarTurno() {
        long id = vista.pedirId();
        vista.mostrarMensaje("Ingrese los nuevos datos para el turno con ID: " + id);
        DatoTurno dato = vista.pedirDatosTurno();
        if (dato == null) { vista.pausar(); return; }
        try {
            servicioTurno.actualizarTurno(id, dato.getIdPaciente(), dato.getIdOdontologo(),
                    dato.getFecha(), dato.getHora());
            vista.mostrarMensaje("Turno actualizado correctamente.");
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void listarTurnos() {
        List<Turno> turnos = servicioTurno.listarTodos();
        if (turnos.isEmpty()) {
            vista.mostrarMensaje("No hay turnos registrados.");
        } else {
            System.out.println("\n--- LISTA DE TURNOS ---");
            for (Turno t : turnos) System.out.println(t);
        }
        vista.pausar();
    }

    private void confirmarTurno() {
        long id = vista.pedirId();
        try {
            servicioTurno.confirmarTurno(id);
            vista.mostrarMensaje("Turno confirmado correctamente.");
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void cancelarTurno() {
        long id = vista.pedirId();
        try {
            servicioTurno.cancelarTurno(id);
            vista.mostrarMensaje("Turno cancelado correctamente.");
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void completarTurno() {
        long id = vista.pedirId();
        try {
            servicioTurno.completarTurno(id);
            vista.mostrarMensaje("Turno completado correctamente.");
        } catch (ClinicaException e) {
            vista.mostrarError(e.getMessage());
        }
        vista.pausar();
    }

    private void buscarTurnosPorRangoFechas() {
        LocalDate[] rango = vista.pedirRangoFechas();
        if (rango == null) { vista.pausar(); return; }
        List<Turno> turnos = servicioTurno.buscarPorRangoFechas(rango[0], rango[1]);
        if (turnos.isEmpty()) {
            vista.mostrarMensaje("No hay turnos en ese rango de fechas.");
        } else {
            System.out.println("\n--- TURNOS DEL " + rango[0] + " AL " + rango[1] + " ---");
            for (Turno t : turnos) System.out.println(t);
        }
        vista.pausar();
    }

    private void filtrarTurnosPorOdontologo() {
        long id = vista.pedirId();
        List<Turno> turnos = servicioTurno.filtrarPorOdontologo(id);
        if (turnos.isEmpty()) {
            vista.mostrarMensaje("No hay turnos registrados para el odontologo con ID: " + id);
        } else {
            System.out.println("\n--- TURNOS DEL ODONTOLOGO #" + id + " ---");
            for (Turno t : turnos) System.out.println(t);
        }
        vista.pausar();
    }

    private void filtrarTurnosPorPaciente() {
        long id = vista.pedirId();
        List<Turno> turnos = servicioTurno.filtrarPorPaciente(id);
        if (turnos.isEmpty()) {
            vista.mostrarMensaje("No hay turnos registrados para el paciente con ID: " + id);
        } else {
            System.out.println("\n--- TURNOS DEL PACIENTE #" + id + " ---");
            for (Turno t : turnos) System.out.println(t);
        }
        vista.pausar();
    }

    // ===================== SALIR =====================

    private void salir() {
        if (vista.pedirConfirmacion("Confirma que desea salir del sistema?")) {
            ejecutando = false;
            vista.mostrarMensaje("Hasta luego!");
        }
    }
}
