package presencial;

public class Estudiante extends Lector {
    private String codigoEstudiante;
    private Boolean esAlumnoRegular;

    public Estudiante(String nombre, String apellido, Integer cedula, String codigoEstudiante) {
        super(nombre, apellido, cedula);
        this.codigoEstudiante = codigoEstudiante;
        this.esAlumnoRegular=true;

    }

    public Boolean getEsAlumnoRegular() {
        return esAlumnoRegular;
    }

    @Override
    public void reservarMaterial(Pedido pedido) {

    }
}
