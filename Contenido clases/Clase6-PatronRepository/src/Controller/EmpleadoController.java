package Controller;

import Entity.*;
import Service.EmpleadoServiceImpl;
import view.Dato;
import view.DatoRD;
import view.DatoM;
import view.VistaEmpleado;
import java.util.List;

public class EmpleadoController implements Runnable {

    private EmpleadoServiceImpl empleadoService;
    private VistaEmpleado vista;
    private boolean ejecutando;

    public EmpleadoController(VistaEmpleado vista) {
        this.empleadoService = new EmpleadoServiceImpl();
        this.vista = vista;
        this.ejecutando = true;
    }

    @Override
    public void run() {
        vista.mostrarMensaje("Sistema iniciado");

        while (ejecutando) {
            int opcion = vista.mostrarMenuInicial();

            switch (opcion) {
                case 1: registrarEmpleado(); break;
                case 2: buscarEmpleado(); break;
                case 3: eliminarEmpleado(); break;
                case 4: listarEmpleados(); break;
                case 5: calcularSalario(); break;
                case 6: salir(); break;
                default: vista.mostrarMensaje("Opcion invalida");
            }
        }

        vista.cerrar();
    }

    private void registrarEmpleado() {
        Dato dato = vista.registroEmpleado();

        Empleado empleado = null;

        if (dato instanceof DatoRD) {
            DatoRD datoRD = (DatoRD) dato;
            empleado = new EmpleadoRelacionDependencia(
                    datoRD.getId(),
                    datoRD.getNombre(),
                    datoRD.getApellido(),
                    datoRD.getCorreo(),
                    datoRD.getSalarioBasico(),
                    datoRD.getAntiguedad(),
                    datoRD.isObraSocial()
            );
        } else if (dato instanceof DatoM) {
            DatoM datoM = (DatoM) dato;
            empleado = new EmpleadoMonotributo(
                    datoM.getId(),
                    datoM.getNombre(),
                    datoM.getApellido(),
                    datoM.getCorreo(),
                    datoM.getSalarioBasico(),
                    datoM.getCategoria(),
                    datoM.getCuotaMensual()
            );
        }

        if (empleado != null) {
            Empleado registrado = empleadoService.registrar(empleado);
            if (registrado != null) {
                vista.mostrarMensaje("Empleado registrado exitosamente");
                vista.mostrarDatosEmpleado(
                        String.valueOf(registrado.getId()),
                        registrado.obtenerNombreCompleto(),
                        registrado.getTipoEmpleado(),
                        registrado.getSalarioBase(),
                        registrado.calcularSalario()
                );
            }
        }

        vista.pausar();
    }

    private void buscarEmpleado() {
        int id = vista.pedirId();
        Empleado emp = empleadoService.buscarPorId(id);

        if (emp != null) {
            vista.mostrarDatosEmpleado(
                    String.valueOf(emp.getId()),
                    emp.obtenerNombreCompleto(),
                    emp.getTipoEmpleado(),
                    emp.getSalarioBase(),
                    emp.calcularSalario()
            );
        } else {
            vista.mostrarMensaje("No se encontro empleado con ID: " + id);
        }

        vista.pausar();
    }

    private void eliminarEmpleado() {
        int id = vista.pedirId();
        Empleado emp = empleadoService.buscarPorId(id);

        if (emp != null) {
            boolean confirmar = vista.pedirConfirmacion("Eliminar a " + emp.obtenerNombreCompleto() + "?");

            if (confirmar) {
                empleadoService.eliminarPorId(id);
                vista.mostrarMensaje("Empleado eliminado");
            } else {
                vista.mostrarMensaje("Operacion cancelada");
            }
        } else {
            vista.mostrarMensaje("No se encontro empleado con ID: " + id);
        }

        vista.pausar();
    }

    private void listarEmpleados() {
        List<Empleado> empleados = empleadoService.listarTodos();

        if (empleados.isEmpty()) {
            vista.mostrarMensaje("No hay empleados registrados");
        } else {
            vista.mostrarMensaje("\n--- LISTA DE EMPLEADOS ---");
            for (Empleado emp : empleados) {
                vista.mostrarDatosEmpleado(
                        String.valueOf(emp.getId()),
                        emp.obtenerNombreCompleto(),
                        emp.getTipoEmpleado(),
                        emp.getSalarioBase(),
                        emp.calcularSalario()
                );
                System.out.println("---------------------------");
            }
        }

        vista.pausar();
    }

    private void calcularSalario() {
        int id = vista.pedirId();
        Empleado emp = empleadoService.buscarPorId(id);

        if (emp != null) {
            vista.mostrarCalculoSalario(
                    emp.obtenerNombreCompleto(),
                    emp.getTipoEmpleado(),
                    emp.getSalarioBase(),
                    emp.calcularImpuesto(),
                    emp.calcularSalario()
            );
        } else {
            vista.mostrarMensaje("No se encontro empleado con ID: " + id);
        }

        vista.pausar();
    }

    private void salir() {
        boolean confirmar = vista.pedirConfirmacion("Esta seguro que desea salir?");

        if (confirmar) {
            ejecutando = false;
            vista.mostrarMensaje("Sistema finalizado");
        }
    }

}