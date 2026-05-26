package view;

import java.util.Scanner;

public class VistaEmpleado {
    private Scanner scanner;

    public VistaEmpleado() {
        scanner = new Scanner(System.in);
    }

    public int mostrarMenuInicial() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Registro de Empleado");
        System.out.println("2. Buscar un empleado");
        System.out.println("3. Eliminar un empleado");
        System.out.println("4. Listar todos los empleados");
        System.out.println("5. Calculadora de Salario");
        System.out.println("6. Salir");
        System.out.print("Opcion: ");

        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    public Dato registroEmpleado() {
        System.out.println("\n--- REGISTRO DE EMPLEADO ---");

        System.out.println("Tipo de Empleado:");
        System.out.println("1. Relacion Dependencia (RD)");
        System.out.println("2. Monotributo (MT)");
        System.out.print("Seleccione tipo: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        Dato dato;

        if (tipo == 1) {
            dato = new DatoRD();
            System.out.print("Anios de antiguedad: ");
            ((DatoRD) dato).setAntiguedad(scanner.nextInt());
            scanner.nextLine();
            System.out.print("Tiene obra social? (S/N): ");
            ((DatoRD) dato).setObraSocial(scanner.nextLine().trim().equalsIgnoreCase("S"));
        } else {
            dato = new DatoM();
            System.out.print("Categoria (A/B/C/D): ");
            ((DatoM) dato).setCategoria(scanner.nextLine().trim().toUpperCase());
            System.out.print("Cuota mensual: ");
            ((DatoM) dato).setCuotaMensual(scanner.nextDouble());
            scanner.nextLine();
        }

        System.out.print("ID: ");
        dato.setId(scanner.nextInt());
        scanner.nextLine();

        System.out.print("Nombre: ");
        dato.setNombre(scanner.nextLine().trim());

        System.out.print("Apellido: ");
        dato.setApellido(scanner.nextLine().trim());

        System.out.print("Correo electronico: ");
        dato.setCorreo(scanner.nextLine().trim());

        System.out.print("Salario Basico: ");
        dato.setSalarioBasico(scanner.nextDouble());
        scanner.nextLine();

        dato.setTipo(tipo);

        return dato;
    }

    public int pedirId() {
        System.out.print("\nIngrese ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String error) {
        System.out.println("ERROR: " + error);
    }

    public void mostrarDatosEmpleado(String id, String nombreCompleto, String tipo,
                                     double salarioBase, double salarioNeto) {
        System.out.println("\n--- DATOS DEL EMPLEADO ---");
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombreCompleto);
        System.out.println("Tipo: " + tipo);
        System.out.println("Salario Base: $" + salarioBase);
        System.out.println("Salario Neto: $" + salarioNeto);
    }

    public void mostrarCalculoSalario(String nombre, String tipo, double salarioBase,
                                      double impuesto, double salarioNeto) {
        System.out.println("\n--- CALCULO DE SALARIO ---");
        System.out.println("Empleado: " + nombre);
        System.out.println("Tipo: " + tipo);
        System.out.println("Salario Base: $" + salarioBase);
        System.out.println("Impuesto: $" + impuesto);
        System.out.println("Salario Neto: $" + salarioNeto);
    }

    public boolean pedirConfirmacion(String mensaje) {
        System.out.print(mensaje + " (S/N): ");
        String respuesta = scanner.nextLine().trim();
        return respuesta.equalsIgnoreCase("S");
    }

    public void pausar() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }

    public void cerrar() {
        if (scanner != null) {
            scanner.close();
        }
    }

}