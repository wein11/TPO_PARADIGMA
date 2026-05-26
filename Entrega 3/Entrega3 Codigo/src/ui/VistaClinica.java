package ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class VistaClinica {

    private Scanner scanner;

    public VistaClinica() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuInicial() {
        System.out.println("\n========================================");
        System.out.println("      CLINICA ODONTOLOGICA SONRISA FELIZ");
        System.out.println("========================================");
        System.out.println("  1. Gestion de Pacientes");
        System.out.println("  2. Gestion de Odontologos");
        System.out.println("  3. Gestion de Turnos");
        System.out.println("  0. Salir");
        System.out.println("========================================");
        System.out.print("Seleccione una opcion: ");
    }

    public void mostrarMenuPacientes() {
        System.out.println("\n--- MENU PACIENTES ---");
        System.out.println("  1. Registrar paciente");
        System.out.println("  2. Buscar paciente por ID");
        System.out.println("  3. Eliminar paciente");
        System.out.println("  4. Actualizar paciente");
        System.out.println("  5. Listar todos los pacientes");
        System.out.println("  6. Mostrar historial clinico");
        System.out.println("  7. Buscar paciente por DNI");
        System.out.println("  8. Listar pacientes ordenados por apellido");
        System.out.println("  0. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
    }

    public void mostrarMenuOdontologos() {
        System.out.println("\n--- MENU ODONTOLOGOS ---");
        System.out.println("  1. Registrar odontologo");
        System.out.println("  2. Buscar odontologo por ID");
        System.out.println("  3. Eliminar odontologo");
        System.out.println("  4. Actualizar odontologo");
        System.out.println("  5. Listar todos los odontologos");
        System.out.println("  0. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
    }

    public void mostrarMenuTurnos() {
        System.out.println("\n--- MENU TURNOS ---");
        System.out.println("  1. Registrar turno");
        System.out.println("  2. Buscar turno por ID");
        System.out.println("  3. Eliminar turno");
        System.out.println("  4. Actualizar turno");
        System.out.println("  5. Listar todos los turnos");
        System.out.println("  6. Confirmar turno");
        System.out.println("  7. Cancelar turno");
        System.out.println("  8. Completar turno");
        System.out.println("  9. Buscar turnos por rango de fechas");
        System.out.println("  10. Filtrar turnos por odontologo");
        System.out.println("  11. Filtrar turnos por paciente");
        System.out.println("  0. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
    }

    public int pedirOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public long pedirId() {
        System.out.print("Ingrese el ID: ");
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public boolean pedirConfirmacion(String mensaje) {
        System.out.print(mensaje + " (s/n): ");
        return scanner.nextLine().trim().equalsIgnoreCase("s");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println("[OK] " + mensaje);
    }

    public void mostrarError(String error) {
        System.out.println("[ERROR] " + error);
    }

    public void pausar() {
        System.out.println("Presione ENTER para continuar...");
        scanner.nextLine();
    }

    public void cerrar() {
        scanner.close();
    }

    public DatoPaciente pedirDatosPaciente() {
        DatoPaciente dato = new DatoPaciente();
        System.out.print("Nombre: ");
        dato.setNombre(scanner.nextLine().trim());
        System.out.print("Apellido: ");
        dato.setApellido(scanner.nextLine().trim());
        System.out.print("DNI: ");
        try {
            dato.setDni(Integer.parseInt(scanner.nextLine().trim()));
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] El DNI debe ser un numero entero.");
            return null;
        }
        System.out.print("Email: ");
        dato.setEmail(scanner.nextLine().trim());
        System.out.print("Fecha de ingreso (AAAA-MM-DD): ");
        try {
            dato.setFechaIngreso(LocalDate.parse(scanner.nextLine().trim()));
        } catch (Exception e) {
            System.out.println("[ERROR] Formato de fecha invalido. Use AAAA-MM-DD (ej: 2024-03-15).");
            return null;
        }
        System.out.println("-- Domicilio --");
        System.out.print("Calle: ");
        dato.setCalle(scanner.nextLine().trim());
        System.out.print("Numero: ");
        try {
            dato.setNumeroCalle(Integer.parseInt(scanner.nextLine().trim()));
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] El numero de calle debe ser un numero entero.");
            return null;
        }
        System.out.print("Localidad: ");
        dato.setLocalidad(scanner.nextLine().trim());
        System.out.print("Provincia: ");
        dato.setProvincia(scanner.nextLine().trim());
        return dato;
    }

    public DatoOdontologo pedirDatosOdontologo() {
        DatoOdontologo dato = new DatoOdontologo();
        System.out.print("Nombre: ");
        dato.setNombre(scanner.nextLine().trim());
        System.out.print("Apellido: ");
        dato.setApellido(scanner.nextLine().trim());
        System.out.print("Matricula (ej: MAT-001): ");
        dato.setMatricula(scanner.nextLine().trim());
        System.out.println("Tipo de especialista: 1=Ortodoncista  2=Endodoncista");
        System.out.print("Opcion: ");
        try {
            dato.setTipoEspecialista(Integer.parseInt(scanner.nextLine().trim()));
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] La opcion debe ser 1 (Ortodoncista) o 2 (Endodoncista).");
            return null;
        }
        return dato;
    }

    public int pedirDni() {
        System.out.print("Ingrese el DNI: ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            mostrarError("DNI invalido.");
            return -1;
        }
    }

    public LocalDate[] pedirRangoFechas() {
        try {
            System.out.print("Fecha inicio (AAAA-MM-DD): ");
            LocalDate inicio = LocalDate.parse(scanner.nextLine().trim());
            System.out.print("Fecha fin   (AAAA-MM-DD): ");
            LocalDate fin = LocalDate.parse(scanner.nextLine().trim());
            return new LocalDate[]{inicio, fin};
        } catch (Exception e) {
            mostrarError("Formato de fecha invalido. Use AAAA-MM-DD.");
            return null;
        }
    }

    public DatoTurno pedirDatosTurno() {
        DatoTurno dato = new DatoTurno();
        System.out.print("ID del Paciente: ");
        try {
            dato.setIdPaciente(Long.parseLong(scanner.nextLine().trim()));
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] El ID del paciente debe ser un numero entero.");
            return null;
        }
        System.out.print("ID del Odontologo: ");
        try {
            dato.setIdOdontologo(Long.parseLong(scanner.nextLine().trim()));
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] El ID del odontologo debe ser un numero entero.");
            return null;
        }
        System.out.print("Fecha (AAAA-MM-DD): ");
        try {
            dato.setFecha(LocalDate.parse(scanner.nextLine().trim()));
        } catch (Exception e) {
            System.out.println("[ERROR] Formato de fecha invalido. Use AAAA-MM-DD (ej: 2026-06-15).");
            return null;
        }
        System.out.print("Hora (HH:MM): ");
        try {
            dato.setHora(LocalTime.parse(scanner.nextLine().trim()));
        } catch (Exception e) {
            System.out.println("[ERROR] Formato de hora invalido. Use HH:MM (ej: 09:00 o 14:30).");
            return null;
        }
        return dato;
    }
}
