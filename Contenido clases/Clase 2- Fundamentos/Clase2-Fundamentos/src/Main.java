import Modelo.Paciente;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Paciente paciente= new Paciente(1,"Esteban","Armando",111111,"estebanarmando@gmail.com", LocalDate.of(2026,03,17));
        Paciente paciente2= new Paciente(2,"Luis enrique","Perez",2222222,"carla@gmail.com");
        paciente.setCuil(2222222);
        paciente2.setCuil(222);
        paciente.getCuil().
        System.out.println("--------------Bienvenido al sistema de pacientes------------");
        System.out.println("Los datos cargados son: "+paciente);
        System.out.println("valor actual nombre: "+paciente.getNombre());
        paciente.setNombre("Luis Enrique");
        System.out.println("Nombre Actual cargado: "+paciente.getNombre());
        System.out.println("____________________________________________________");
        Boolean estado= false;
         paciente.mostrarDatosPaciente(estado);
        System.out.println("_____________________________________________________");
        paciente2.setFechaIngreso(LocalDate.now());
        estado= true;
        paciente2.mostrarDatosPaciente(estado);
        System.out.println("_____________________Paciente String________________________________");
        System.out.println(paciente.mostrarDatosString());
        System.out.println("-------------------comparando nombres---------------");
        System.out.println("Funcion Equals Ignore Case");
        System.out.println(paciente.getNombre().equalsIgnoreCase(paciente2.getNombre()));
        System.out.println(paciente.compararNombrePaciente(paciente2));

    }
}
