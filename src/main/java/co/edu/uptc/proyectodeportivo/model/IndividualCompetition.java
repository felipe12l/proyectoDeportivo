package co.edu.uptc.proyectodeportivo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndividualCompetition extends Competition {
    private List<Affiliate> competitors;
    public IndividualCompetition() {
        competitors = new ArrayList<>();
    }

    public IndividualCompetition(String id, Discipline discipline, String name, Date date, String place, String description) {
        super(id, discipline, name, date, place, description);
        this.competitors = new ArrayList<>();
    }

    public List<Affiliate> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(List<Affiliate> competitors) {
        this.competitors = competitors;
    }
}
