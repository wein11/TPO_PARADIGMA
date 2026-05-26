import modelo.Cliente;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.Repartidor;
import servicio.ServicioCliente;
import servicio.ServicioPedido;
import servicio.iServicio;

public class TrigSoft {
    public static void main(String[] args) {
        //servicios
        iServicio<Cliente> servicioCliente= new ServicioCliente();
        iServicio<Pedido> servicioPedido= new ServicioPedido();
        //crear el cliente con direccion
        Cliente.Direccion dirTeresa= new Cliente.Direccion("Av San Martin",1234,"3 B");
        Cliente teresa= new Cliente(1,"Teresa","+5411354545",dirTeresa,"ninguna");
        teresa.setCondicionesEspeciales("Alergia a la manteca");
        servicioCliente.agregar(teresa);
        //crear repartidor
        Repartidor raul= new Repartidor(1,"Raul Alfonso","+431134343","Monserrat");
        //crear un pedido
        Pedido pedido= new Pedido(101,teresa);
        pedido.agregarLinea(new Pedido.LineaPedido("medialuna",12));
        pedido.agregarLinea(new Pedido.LineaPedido("pan de campo",2));
        pedido.asignarRepartidor(raul);

        //listener con clase anonima
        pedido.setEstadoChangeListener(new Pedido.EstadoChangeListener() {
            @Override
            public void estadoCambiado(Pedido pedido, EstadoPedido viejo, EstadoPedido nuevo) {
                System.out.println("\uD83D\uDD14 [Notificación]: # "+pedido.getId()+" cambió  de: "+viejo+" a: "+nuevo);

            }
        });
        //simular los cambios de estado
        pedido.cambiarEstado(EstadoPedido.CONFIRMADO);
        pedido.cambiarEstado(EstadoPedido.EN_PREPARACION);
        pedido.cambiarEstado(EstadoPedido.EN_ENVIO);
        //guardarlo
        servicioPedido.agregar(pedido);
        //mostrar pedidos en curso
        System.out.println("\n---Pedidos en el sistema----");
        for(Pedido pedido2: servicioPedido.listarTodos()){
            System.out.println(pedido2);
        }
    }
}
