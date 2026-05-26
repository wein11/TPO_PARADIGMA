package modelo;

public final class Cliente extends Persona{
    private  Direccion direccion;
    private String condicionesEspeciales;

    public Cliente(int id, String nombre, String telefono, Direccion direccion, String condicionesEspeciales) {
        super(id, nombre, telefono);
        this.direccion = direccion;
        this.condicionesEspeciales = condicionesEspeciales;
    }

    //clase interna estatica
    public static class Direccion{
        private String calle;
        private  int altura;
        private String pisoDpto;

        public Direccion(String calle, int altura, String pisoDpto){
            this.calle= calle;
            this.altura= altura;
            this.pisoDpto= pisoDpto;
        }
        @Override
        public String toString() {
            return calle+ " "+altura+ ", "+pisoDpto;
        }
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getCondicionesEspeciales() {
        return condicionesEspeciales;
    }

    public void setCondicionesEspeciales(String condicionesEspeciales) {
        this.condicionesEspeciales = condicionesEspeciales;
    }

    @Override
    public String toString() {
        return getNombre()+" ( "+direccion+ ") ";
    }
}
