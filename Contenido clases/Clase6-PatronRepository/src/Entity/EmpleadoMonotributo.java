package Entity;

public class EmpleadoMonotributo extends Empleado{
    private String categoria;
    private double cuotaMensual;

    public EmpleadoMonotributo() {
    }

    public EmpleadoMonotributo(int id, String nombre, String apellido, String email,
                               double salarioBase, String categoria, double cuotaMensual) {
        super(id, nombre, apellido, email, salarioBase);
        this.categoria = categoria;
        this.cuotaMensual = cuotaMensual;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(double cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }

    @Override
    public double calcularImpuesto() {
        return salarioBase * 0.05;
    }

    @Override
    public double calcularSalario() {
        double impuesto = calcularImpuesto();
        return salarioBase - impuesto - cuotaMensual;
    }

    @Override
    public String getTipoEmpleado() {
        return "Monotributo - Categoria " + categoria;
    }
}
