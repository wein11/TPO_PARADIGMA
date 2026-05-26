package Modelo;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class Paciente {
    // formato atributos: encapsulamiento- tipoDato- nombre;
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer dni;
    private  int cuil;
    private String email;
    private LocalDate fechaIngreso;

    //metodos
    //constructor: el metodo que permite la creacion de los objetos
    /*Unico metodo que se llama como la clase misma*/
    /* no tiene salida*/
    public Paciente(Integer id, String nombre, String apellido, Integer dni, String email, LocalDate fechaIngreso) {
        //cuerpo del metodo
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
    }

    public Paciente(Integer id, String nombre, String apellido, Integer dni, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
    }

    public int getCuil() {
        return cuil;
    }

    public void setCuil(int cuil) {
        this.cuil = cuil;
    }

    //encapsulamuento- salida- nombre-parametros-exception(opcional){}
    public String getNombre() {
        return this.nombre;
    }

    public Integer getId() {
        return this.id;
    }

    public String getApellido() {
        return apellido;
    }

    public Integer getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void mostrarDatosPaciente(Boolean estado) {
        if (estado == true) {
            System.out.println("Datos del paciente cargado: " + getId());
            System.out.println("Nombre: " + getNombre());
            System.out.println("Apellido: " + this.apellido);
            System.out.println("Cedula: " + getDni());
            System.out.println("Email: " + getEmail());
            System.out.println("Fecha de Ingreso: " + getFechaIngreso());
        }
        System.out.println("No se permite mostrar los datos");
    }

    public String mostrarDatosString() {
        return "Nombre: " + this.nombre + " -Apellido: " + getApellido() + " -Cedula: " + this.dni;
    }
    public Boolean compararNombrePaciente(Paciente otroPaciente){
        if(this.nombre== otroPaciente.getNombre()){
            return true;
        }
        return false;
    }
}
