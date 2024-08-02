package co.edu.uptc.proyectodeportivo.logic;

import java.util.List;

public class Competition {

    private String name;
    private String date;
    private Discipline discipline;

    public Competition(String name, String date, Discipline discipline) {
        this.name = name;
        this.date = date;
        this.discipline = discipline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
}
