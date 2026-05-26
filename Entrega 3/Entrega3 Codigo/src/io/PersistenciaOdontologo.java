package io;

import model.Odontologo;
import model.OdontologoEndodoncista;
import model.OdontologoOrtodoncista;
import repository.RepositorioOdontologo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PersistenciaOdontologo {

    private static final String ARCHIVO = "odontologos.csv";
    private static final String SEPARADOR = ";";

    // Guarda todos los odontólogos en el archivo CSV
    public static void guardar(List<Odontologo> odontologos) {
        BufferedWriter escritor = null;
        try {
            escritor = new BufferedWriter(new FileWriter(ARCHIVO));
            for (Odontologo o : odontologos) {
                escritor.write(serializar(o));
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo guardar odontologos.csv: " + e.getMessage());
        } finally {
            if (escritor != null) {
                try { escritor.close(); } catch (IOException e) { /* ignorado */ }
            }
        }
    }

    // Carga los odontólogos desde el archivo CSV al repositorio
    public static void cargar(RepositorioOdontologo repositorio) {
        BufferedReader lector = null;
        try {
            lector = new BufferedReader(new FileReader(ARCHIVO));
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!linea.isBlank()) {
                    Odontologo odontologo = deserializar(linea);
                    if (odontologo != null) repositorio.cargarEntidad(odontologo);
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

    private static String serializar(Odontologo o) {
        // tipo: 1=Ortodoncista, 2=Endodoncista
        String tipo = (o instanceof OdontologoOrtodoncista) ? "1" : "2";
        return o.getId() + SEPARADOR
                + o.getNombre() + SEPARADOR
                + o.getApellido() + SEPARADOR
                + o.getMatricula() + SEPARADOR
                + tipo;
    }

    private static Odontologo deserializar(String linea) {
        try {
            String[] campos = linea.split(SEPARADOR, -1);
            long id          = Long.parseLong(campos[0]);
            String nombre    = campos[1];
            String apellido  = campos[2];
            String matricula = campos[3];
            int tipo         = Integer.parseInt(campos[4]);
            if (tipo == 1) return new OdontologoOrtodoncista(id, nombre, apellido, matricula);
            if (tipo == 2) return new OdontologoEndodoncista(id, nombre, apellido, matricula);
            System.out.println("[ERROR] Tipo de odontologo desconocido en odontologos.csv: " + tipo);
            return null;
        } catch (Exception e) {
            System.out.println("[ERROR] Linea invalida en odontologos.csv: " + linea);
            return null;
        }
    }
}
