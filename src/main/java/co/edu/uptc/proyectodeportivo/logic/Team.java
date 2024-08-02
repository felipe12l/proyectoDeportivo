package co.edu.uptc.proyectodeportivo.logic;

import java.util.List;

public class Team {
    private String name;
    private List<User> participants;
    private Discipline discipline;

    public Team(String name, List<User> participants,Discipline discipline) {
        this.name = name;
        this.participants = participants;
        this.discipline = discipline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
}
