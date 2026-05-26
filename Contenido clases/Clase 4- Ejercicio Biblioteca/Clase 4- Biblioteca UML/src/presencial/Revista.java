package presencial;

public class Revista extends Material {
    private String anioPublicion;

    public Revista(String titulo, String autor, String editorial, String anioPublicion) {
        super(titulo, autor, editorial);
        this.anioPublicion = anioPublicion;
    }
}
