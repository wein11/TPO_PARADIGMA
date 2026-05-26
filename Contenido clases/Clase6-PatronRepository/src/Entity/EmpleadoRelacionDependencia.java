package Entity;

public class EmpleadoRelacionDependencia extends Empleado {
    private int antiguedad;
    private boolean obraSocial;

    public EmpleadoRelacionDependencia() {
    }

    public EmpleadoRelacionDependencia(int id, String nombre, String apellido, String email,
                                       double salarioBase, int antiguedad, boolean obraSocial) {
        super(id, nombre, apellido, email, salarioBase);
        this.antiguedad = antiguedad;
        this.obraSocial = obraSocial;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public boolean isObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(boolean obraSocial) {
        this.obraSocial = obraSocial;
    }

    @Override
    public double calcularImpuesto() {
        return salarioBase * 0.17;
    }

    @Override
    public double calcularSalario() {
        double bono = antiguedad * 1000;
        double impuesto = calcularImpuesto();
        double descuentoObraSocial = obraSocial ? salarioBase * 0.03 : 0;
        return salarioBase + bono - impuesto - descuentoObraSocial;
    }

    @Override
    public String getTipoEmpleado() {
        return "Relacion Dependencia";
    }

}
