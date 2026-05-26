package Presencial;

public class Mate {
    //ATRIBUTOS: son las caracteristicas de definen una clase
    //variables
    private String material;
    private int tamanio;
    private String color;
    private Double capacidad;
    /*METODOS*/

    public Mate() {
    }

    public Mate(String material, int tamanio, String color, Double capacidad) {
        this.material = material;
        this.tamanio = tamanio;
        this.color = color;
        this.capacidad = capacidad;
    }

    public Mate(String material, int tamanio, Double capacidad) {
        this.material = material;
        this.tamanio = tamanio;
        this.capacidad = capacidad;
    }
//encapsulamiento- salida- nombreDelMetodo-parametros(opcional)
    public Boolean estaLavadoElMate(){
        //cuerpo TODA LA LOGICA
        return false;
    }

}
