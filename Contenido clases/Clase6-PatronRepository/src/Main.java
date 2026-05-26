import Controller.EmpleadoController;
import view.VistaEmpleado;

public class Main {
    public static void main(String[] args) {

        VistaEmpleado vista = new VistaEmpleado();
        EmpleadoController controller = new EmpleadoController(vista);

        controller.run();
    }
}
