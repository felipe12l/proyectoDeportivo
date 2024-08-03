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
    public Competition findById(String id) {
        return null;
    }

    @Override
    public Competition delete(String id) {
        return null;
    }

    @Override
    public Competition update(Competition object, String id) {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
