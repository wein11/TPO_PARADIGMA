package repository;

import model.Odontologo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioOdontologo implements iRepository<Odontologo> {

    private List<Odontologo> odontologos;
    private long contadorId;

    public RepositorioOdontologo() {
        this.odontologos = new ArrayList<>();
        this.contadorId = 1;
    }

    @Override
    public void guardar(Odontologo odontologo) {
        odontologo.setId(contadorId++);
        odontologos.add(odontologo);
    }

    @Override
    public Odontologo buscarPorId(long id) {
        for (Odontologo o : odontologos) {
            if (o.getId() == id) return o;
        }
        return null;
    }

    @Override
    public void eliminarPorId(long id) {
        odontologos.removeIf(o -> o.getId() == id);
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        for (int i = 0; i < odontologos.size(); i++) {
            if (odontologos.get(i).getId() == odontologo.getId()) {
                odontologos.set(i, odontologo);
                return;
            }
        }
    }

    @Override
    public List<Odontologo> listarTodos() {
        return new ArrayList<>(odontologos);
    }

    // Carga desde CSV: agrega sin modificar el ID y ajusta el contador
    public void cargarEntidad(Odontologo odontologo) {
        odontologos.add(odontologo);
        if (odontologo.getId() >= contadorId) contadorId = odontologo.getId() + 1;
    }

    // Búsqueda por matrícula usando Stream
    public Odontologo buscarPorMatricula(String matricula) {
        return odontologos.stream()
                .filter(o -> o.getMatricula() != null && o.getMatricula().equalsIgnoreCase(matricula))
                .findFirst()
                .orElse(null);
    }

}
