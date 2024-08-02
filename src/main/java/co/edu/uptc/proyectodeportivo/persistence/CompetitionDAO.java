package co.edu.uptc.proyectodeportivo.persistence;

import co.edu.uptc.proyectodeportivo.model.Competition;

import java.io.IOException;
import java.util.List;

public class CompetitionDAO implements InterfaceDAO<Competition> {
    @Override
    public List<Competition> getAll() {
        return List.of();
    }

    @Override
    public Competition save(Competition object) {
        return null;
    }

    @Override
    public Competition findById(Integer id) {
        return null;
    }

    @Override
    public Competition delete(Integer id) {
        return null;
    }

    @Override
    public Competition update(Competition object, Integer id) {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
