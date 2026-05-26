package Presencial;

public class Main {
    public static void main(String[] args) {
        //necesito el objeto
        Persona persona= new Persona("Juan",111111,2,Genero.Masculino);

        System.out.println(persona.esMayorDeEdad());
        persona.conocerDatos();
    }
}
