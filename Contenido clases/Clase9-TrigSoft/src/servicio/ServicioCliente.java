package servicio;

import modelo.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ServicioCliente implements iServicio<Cliente>{
    private List<Cliente> clientes= new ArrayList<>();
    @Override
    public void agregar(Cliente cliente) {
        clientes.add(cliente);
        System.out.println("Cliente agregado con exito");

    }

    @Override
    public Cliente buscarPorId(int id) {
        /*for (Cliente cliente : clientes) {
            if(cliente.getId()==id){
                return  cliente;
            }
        }
        return null;*/
        return clientes.stream().filter(cliente -> cliente.getId()==id).findFirst().orElse(null);
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }

    @Override
    public void actualizar(Cliente cliente) {
Cliente clienteExistente= buscarPorId(cliente.getId());
if(clienteExistente!=null){
    eliminar(cliente.getId());
    agregar(cliente);
}
    }

    @Override
    public void eliminar(int id) {
        clientes.removeIf(cliente -> cliente.getId()==id);
    }
}
