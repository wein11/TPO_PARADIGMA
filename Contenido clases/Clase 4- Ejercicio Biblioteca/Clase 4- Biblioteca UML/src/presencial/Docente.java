package presencial;

public class Docente extends Lector{
    private String codigoDocente;
    private Boolean excedeLimitePermitido;


    public Docente(String nombre, String apellido, Integer cedula, String codigoDocente) {
        super(nombre, apellido, cedula);
        this.codigoDocente = codigoDocente;
        this.excedeLimitePermitido=false;

    }

    public Boolean getExcedeLimitePermitido() {
        return excedeLimitePermitido;
    }

    @Override
    public void reservarMaterial(Pedido pedido) {

    }
}
