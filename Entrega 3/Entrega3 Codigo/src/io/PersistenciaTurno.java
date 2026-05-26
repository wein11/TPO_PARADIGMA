package io;

import model.EstadoTurno;
import model.Odontologo;
import model.Paciente;
import model.Turno;
import repository.RepositorioOdontologo;
import repository.RepositorioPaciente;
import repository.RepositorioTurno;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PersistenciaTurno {

    private static final String ARCHIVO = "turnos.csv";
    private static final String SEPARADOR = ";";

    // Guarda todos los turnos en el archivo CSV
    public static void guardar(List<Turno> turnos) {
        BufferedWriter escritor = null;
        try {
            escritor = new BufferedWriter(new FileWriter(ARCHIVO));
            for (Turno t : turnos) {
                escritor.write(serializar(t));
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo guardar turnos.csv: " + e.getMessage());
        } finally {
            if (escritor != null) {
                try { escritor.close(); } catch (IOException e) { /* ignorado */ }
            }
        }
    }

    // Carga los turnos desde el archivo CSV; resuelve paciente y odontologo por ID
    public static void cargar(RepositorioTurno repositorioTurno,
                               RepositorioPaciente repositorioPaciente,
                               RepositorioOdontologo repositorioOdontologo) {
        BufferedReader lector = null;
        try {
            lector = new BufferedReader(new FileReader(ARCHIVO));
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!linea.isBlank()) {
                    Turno turno = deserializar(linea, repositorioPaciente, repositorioOdontologo);
                    if (turno != null) repositorioTurno.cargarEntidad(turno);
                }
            }
        } catch (IOException e) {
            // Si el archivo no existe todavía, se inicia con lista vacía
        } finally {
            if (lector != null) {
                try { lector.close(); } catch (IOException e) { /* ignorado */ }
            }
        }
    }

    private static String serializar(Turno t) {
        return t.getId() + SEPARADOR
                + t.getPaciente().getId() + SEPARADOR
                + t.getOdontologo().getId() + SEPARADOR
                + t.getFecha() + SEPARADOR
                + t.getHora() + SEPARADOR
                + t.getEstado();
    }

    private static Turno deserializar(String linea, RepositorioPaciente repoPaciente,
                                      RepositorioOdontologo repoOdontologo) {
        try {
            String[] campos  = linea.split(SEPARADOR, -1);
            long id          = Long.parseLong(campos[0]);
            long idPaciente  = Long.parseLong(campos[1]);
            long idOdontologo = Long.parseLong(campos[2]);
            LocalDate fecha  = LocalDate.parse(campos[3]);
            LocalTime hora   = LocalTime.parse(campos[4]);
            EstadoTurno estado = EstadoTurno.valueOf(campos[5]);

            Paciente paciente = repoPaciente.buscarPorId(idPaciente);
            Odontologo odontologo = repoOdontologo.buscarPorId(idOdontologo);

            if (paciente == null) {
                System.out.println("[ERROR] Paciente ID " + idPaciente + " no encontrado al cargar turno #" + id);
                return null;
            }
            if (odontologo == null) {
                System.out.println("[ERROR] Odontologo ID " + idOdontologo + " no encontrado al cargar turno #" + id);
                return null;
            }
            return new Turno(id, paciente, odontologo, fecha, hora, estado);
        } catch (Exception e) {
            System.out.println("[ERROR] Linea invalida en turnos.csv: " + linea);
            return null;
        }
    }
}
