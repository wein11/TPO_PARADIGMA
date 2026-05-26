package presencial;

import java.util.ArrayList;
import java.util.List;

public abstract class Lector {
    private String nombre;
    private String apellido;
    private Integer cedula;
 private Pedido pedido;
    public Lector(String nombre, String apellido, Integer cedula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;

    }

    public abstract void reservarMaterial(Pedido pedido);

    public void realizarDevolucionMaterial(){
            if(this.pedido!=null){
                System.out.println("Devolviendo materiales");
            }
            this.pedido=null;
        System.out.println("usuario sin pedido activo");
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Pedido getPedido() {
        return pedido;
    }
}
