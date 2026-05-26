package view;

public class DatoRD extends Dato {
    private int antiguedad;
    private boolean obraSocial;

    public DatoRD() {
        this.tipo = 1;
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

}