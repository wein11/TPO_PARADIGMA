package modelo;

public final class Repartidor extends Persona {
    private String zona;


    public Repartidor(int id, String nombre, String telefono, String zona) {
        super(id, nombre, telefono);
        this.zona = zona;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}
