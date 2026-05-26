package Entity;

public abstract class Empleado {
    protected int id;
    protected String nombre;
    protected String apellido;
    protected String email;
    protected double salarioBase;

    public Empleado() {
    }

    public Empleado(int id, String nombre, String apellido, String email, double salarioBase) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.salarioBase = salarioBase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public String obtenerNombreCompleto() {
        return nombre + " " + apellido;
    }

    public abstract double calcularSalario();

    public abstract double calcularImpuesto();

    public abstract String getTipoEmpleado();

}