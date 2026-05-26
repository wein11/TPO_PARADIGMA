package servicio;

import modelo.Pedido;

import java.util.ArrayList;
import java.util.List;

public class ServicioPedido implements iServicio<Pedido>{
    private List<Pedido> pedidos= new ArrayList<>();
    @Override
    public void agregar(Pedido pedido) {
        pedidos.add(pedido);
    }

    @Override
    public Pedido buscarPorId(int id) {
        return pedidos.stream().filter(pedido -> pedido.getId()==id).findFirst().orElse(null);
    }

    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos);
    }

    @Override
    public void actualizar(Pedido pedido) {
            Pedido pedidoExistente= buscarPorId(pedido.getId());
            if(pedidoExistente!=null){
                eliminar(pedido.getId());
                agregar(pedido);
            }
    }

    @Override
    public void eliminar(int id) {
        pedidos.removeIf(pedido -> pedido.getId()==id);
    }
}
