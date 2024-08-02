package co.edu.uptc.proyectodeportivo.persistence;

import co.edu.uptc.proyectodeportivo.model.Discipline;

import java.io.IOException;
import java.util.List;

public class DisciplineDAO implements InterfaceDAO<Discipline> {
    @Override
    public List<Discipline> getAll() {
        return List.of();
    }

    @Override
    public Discipline save(Discipline object) {
        return null;
    }

    @Override
    public Discipline findById(Integer id) {
        return null;
    }

    @Override
    public Discipline delete(Integer id) {
        return null;
    }

    @Override
    public Discipline update(Discipline object, Integer id) {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
