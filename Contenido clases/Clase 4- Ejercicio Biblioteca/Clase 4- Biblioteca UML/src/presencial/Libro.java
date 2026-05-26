package presencial;

public class Libro extends Material{
    private String edicion;

    public Libro(String titulo, String autor, String editorial, String edicion) {
        super(titulo, autor, editorial);
        this.edicion = edicion;
    }
}
