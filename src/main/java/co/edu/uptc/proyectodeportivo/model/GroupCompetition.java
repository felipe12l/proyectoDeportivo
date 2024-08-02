package co.edu.uptc.proyectodeportivo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupCompetition extends Competition {
    private List<Group> groups;

    public GroupCompetition() {
        groups = new ArrayList<>();
    }


    public void removeGroup(List<Affiliate> group) {
        groups.remove(group);
    }
}
