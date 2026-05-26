import java.util.ArrayList;

public class Persona {
    //atributos
    private String nombre;
    private String apellido;
    private int edad;
    private Integer cedula;
    // ==================== COMPOSICIÓN ====================
    private FechaNacimiento fechaNacimiento;   // Composición fuerte

    // ==================== AGREGACIÓN ====================
    private ArrayList<Telefono> telefonos;     // Agregación

    // ==================== ASOCIACIÓN SIMPLE ====================
    private Universidad universidad;           // Asociación
   

    public Persona(String nombre, String apellido, int edad, Integer cedula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.cedula = cedula;
    }

    public Persona(String nombre, String apellido, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }
    public Boolean esMayorDeEdad(){
        return this.edad>18;
    }
    public String esMayorDeEdad2(){
        if(edad>18){
            return "SI";
        }
        return "NO";
    }
    // ==================== toString() ====================
    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", edad=" + edad +
                '}';
    }

    // ==================== equals() ====================
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;                    // misma referencia
        if (obj == null || getClass() != obj.getClass()) return false;

        Persona persona = (Persona) obj;

        return edad == persona.edad &&nombre.equals(persona.nombre);
    }
}
