package Repository;

import Entity.Empleado;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepository {

    private List<Empleado> empleados;

    public EmpleadoRepository() {
        empleados = new ArrayList<>();
    }

    public Empleado guardar(Empleado empleado) {
        empleados.add(empleado);
        return empleado;
    }

    public Empleado buscarPorId(int id) {
        for (Empleado emp : empleados) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        return null;
    }

    public void eliminarPorId(int id) {
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getId() == id) {
                empleados.remove(i);
                break;
            }
        }
    }

    public List<Empleado> listarTodos() {
        return empleados;
    }

}