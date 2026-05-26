package modelo;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<LineaPedido> lineas= new ArrayList<>();
    private EstadoPedido estado= EstadoPedido.PENDIENTE;
    private Repartidor repartidor;


    //clase interna estatica para cada linea del pedido
    public static class LineaPedido{
        private String producto;
        private int cantidad;

        public LineaPedido(String producto, int cantidad){
            this.producto= producto;
            this.cantidad= cantidad;
        }

        public String getProducto() {
            return producto;
        }

        public int getCantidad() {
            return cantidad;
        }

        @Override
        public String toString() {
            return "LineaPedido{" +
                    "producto='" + producto + '\'' +
                    ", cantidad=" + cantidad +
                    '}';
        }
    }
    //interfaz interna para el callback de cambio de estado
    public interface EstadoChangeListener{
        void estadoCambiado(Pedido pedido, EstadoPedido viejo, EstadoPedido nuevo);
    }
    private EstadoChangeListener listener;

    public Pedido(int id, Cliente cliente){
        this.id= id;
        this.cliente= cliente;
    }

    public void setListener(EstadoChangeListener listener) {
        this.listener = listener;
    }
    public void cambiarEstado(EstadoPedido nuevo){
        EstadoPedido viejo= this.estado;
        this.estado= nuevo;
        if(listener!=null){
            listener.estadoCambiado(this, viejo,nuevo);
        }
    }
    public void agregarLinea(LineaPedido linea){
        lineas.add(linea);
    }
    public void asignarRepartidor(Repartidor repartidor){this.repartidor= repartidor;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaPedido> lineas) {
        this.lineas = lineas;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public EstadoChangeListener getListener() {
        return listener;
    }
    public void setEstadoChangeListener(EstadoChangeListener listener){

        this.listener= listener;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", lineas=" + lineas +
                ", estado=" + estado +
                ", repartidor=" + repartidor +
                ", listener=" + listener +
                '}';
    }
}
