package io;

import model.Domicilio;
import model.Paciente;
import repository.RepositorioPaciente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PersistenciaPaciente {

    private static final String ARCHIVO = "pacientes.csv";
    private static final String SEPARADOR = ";";

    // Guarda todos los pacientes en el archivo CSV
    public static void guardar(List<Paciente> pacientes) {
        BufferedWriter escritor = null;
        try {
            escritor = new BufferedWriter(new FileWriter(ARCHIVO));
            for (Paciente p : pacientes) {
                escritor.write(serializar(p));
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo guardar pacientes.csv: " + e.getMessage());
        } finally {
            if (escritor != null) {
                try { escritor.close(); } catch (IOException e) { /* ignorado */ }
            }
        }
    }

    // Carga los pacientes desde el archivo CSV al repositorio
    public static void cargar(RepositorioPaciente repositorio) {
        BufferedReader lector = null;
        try {
            lector = new BufferedReader(new FileReader(ARCHIVO));
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!linea.isBlank()) {
                    Paciente paciente = deserializar(linea);
                    if (paciente != null) repositorio.cargarEntidad(paciente);
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

    private static String serializar(Paciente p) {
        return p.getId() + SEPARADOR
                + p.getNombre() + SEPARADOR
                + p.getApellido() + SEPARADOR
                + p.getDni() + SEPARADOR
                + p.getEmail() + SEPARADOR
                + p.getFechaIngreso() + SEPARADOR
                + p.getDomicilio().getCalle() + SEPARADOR
                + p.getDomicilio().getNumero() + SEPARADOR
                + p.getDomicilio().getLocalidad() + SEPARADOR
                + p.getDomicilio().getProvincia() + SEPARADOR
                + (p.getObraSocial() != null ? p.getObraSocial() : "");
    }

    private static Paciente deserializar(String linea) {
        try {
            String[] campos = linea.split(SEPARADOR, -1);
            long id           = Long.parseLong(campos[0]);
            String nombre     = campos[1];
            String apellido   = campos[2];
            int dni           = Integer.parseInt(campos[3]);
            String email      = campos[4];
            LocalDate fecha   = LocalDate.parse(campos[5]);
            String calle      = campos[6];
            int numero        = Integer.parseInt(campos[7]);
            String localidad  = campos[8];
            String provincia  = campos[9];
            String obraSocial = campos[10];
            Domicilio domicilio = new Domicilio(calle, numero, localidad, provincia, "");
            Paciente paciente = new Paciente(id, nombre, apellido, dni, email, fecha, domicilio);
            paciente.setObraSocial(obraSocial.isEmpty() ? null : obraSocial);
            return paciente;
        } catch (Exception e) {
            System.out.println("[ERROR] Linea invalida en pacientes.csv: " + linea);
            return null;
        }
    }
}
