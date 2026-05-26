package presencial;

public class Bibliotecario {
    private String nombre;
    private Pedido pedido;
    private Lector lector;

    public Bibliotecario(String nombre, Pedido pedido, Lector lector) {
        this.nombre = nombre;
        this.pedido = pedido;
        this.lector = lector;
    }

    public void autorizarPedido(){
        if(lector instanceof Estudiante){
            if(((Estudiante) lector).getEsAlumnoRegular()){
                System.out.println("pedido fue autorizado");
                lector.setPedido(pedido);
            }else{
                System.out.println("Su pedido no fue autorizado");
            }
        }else if (lector instanceof Docente) {
            if (((Docente) lector).getExcedeLimitePermitido()) {
                System.out.println("No puede solicitar más libros");
            } else {
                // Solo si NO excede el límite, autorizamos y asignamos
                System.out.println("Pedido fue autorizado");
                lector.setPedido(pedido);

            }
        }
    }
}

