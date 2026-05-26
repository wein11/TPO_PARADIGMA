package Presencial;

public class Persona {
    private String nombre;
    private Integer dni;
    private int edad;
    private Genero genero;


    public Persona(String nombre, Integer dni, int edad, Genero genero) {
        this.nombre = nombre;
        this.dni = dni;
        this.edad = edad; //16
        this.genero = genero;
    }
    public String esMayorDeEdad(){
        //condicion o pregunta
        if(edad>=18){
            return "usted es mayor de edad";
        }else{
            return "usted es menor";
        }
    }
    public void conocerDatos(){
        System.out.println("Los datos cargados de la persona son::::");
        System.out.println("Nombre: "+nombre+" ndoc: "+dni+" edad: "+edad+" genero: "+genero);
    }
}
