package main;

import io.PersistenciaOdontologo;
import io.PersistenciaPaciente;
import io.PersistenciaTurno;
import repository.RepositorioOdontologo;
import repository.RepositorioPaciente;
import repository.RepositorioTurno;
import service.OdontologoServiceImpl;
import service.PacienteServiceImpl;
import service.TurnoServiceImpl;
import ui.ClinicaController;
import ui.VistaClinica;

public class Main {

    public static void main(String[] args) {

        // Crear repositorios vacíos
        RepositorioPaciente repoPaciente     = new RepositorioPaciente();
        RepositorioOdontologo repoOdontologo = new RepositorioOdontologo();
        RepositorioTurno repoTurno           = new RepositorioTurno();

        // Cargar datos desde CSV al iniciar
        // Orden obligatorio: turnos dependen de pacientes y odontólogos ya cargados
        PersistenciaPaciente.cargar(repoPaciente);
        PersistenciaOdontologo.cargar(repoOdontologo);
        PersistenciaTurno.cargar(repoTurno, repoPaciente, repoOdontologo);

        // Crear servicios con los repositorios ya cargados
        PacienteServiceImpl servicioPaciente     = new PacienteServiceImpl(repoPaciente);
        OdontologoServiceImpl servicioOdontologo = new OdontologoServiceImpl(repoOdontologo);
        TurnoServiceImpl servicioTurno           = new TurnoServiceImpl(servicioPaciente, servicioOdontologo, repoTurno);

        // Iniciar la interfaz
        VistaClinica vista = new VistaClinica();
        ClinicaController controller = new ClinicaController(vista, servicioPaciente, servicioOdontologo, servicioTurno);

        try {
            controller.run();
        } finally {
            // Guardar datos al cerrar, siempre — incluso ante errores inesperados
            PersistenciaPaciente.guardar(repoPaciente.listarTodos());
            PersistenciaOdontologo.guardar(repoOdontologo.listarTodos());
            PersistenciaTurno.guardar(repoTurno.listarTodos());
        }
    }
}
