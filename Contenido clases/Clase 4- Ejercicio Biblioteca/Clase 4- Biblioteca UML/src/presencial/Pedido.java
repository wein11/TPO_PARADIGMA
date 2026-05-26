package presencial;

import java.util.ArrayList;
import java.util.List;

public abstract class Pedido {
    private Lector lector;
    private List<Material> materiales;

    public Pedido(Lector lector) {
        this.lector = lector;
        materiales= new ArrayList<>();
    }


    public void agregarMaterialAlCarrito(Material material){
        materiales.add(material);
    }
}
