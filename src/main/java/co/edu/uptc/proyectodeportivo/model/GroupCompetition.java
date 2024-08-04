package co.edu.uptc.proyectodeportivo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupCompetition extends Competition {
    private List<Group> groups;

    public GroupCompetition() {
        groups = new ArrayList<>();
    }

    public GroupCompetition(String id, Discipline discipline, String name, Date date, String place, String description) {
        super(id, discipline, name, date, place, description);
        groups = new ArrayList<>();
    }

    public void removeGroup(List<Affiliate> group) {
        groups.remove(group);
    }
}
