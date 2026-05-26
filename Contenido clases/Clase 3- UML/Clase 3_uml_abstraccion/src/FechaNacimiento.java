// ==================== COMPOSICIÓN ====================
// La fecha de nacimiento NO tiene sentido sin una persona
public class FechaNacimiento {
    private int dia, mes, anio;

    public FechaNacimiento(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    @Override
    public String toString() {
        return dia + "/" + mes + "/" + anio;
    }
}
