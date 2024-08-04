package co.edu.uptc.proyectodeportivo.model;

import java.util.Date;

public class Competition {
    private String id;
    private Discipline discipline;
    private String name;
    private Date date;
    private String place;
    private String description;
    public Competition() {
    }

    @Override
    public String toString() {
        return "Competition{" +
                "discipline=" + discipline +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", place='" + place + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Competition(String id, Discipline discipline, String name, Date date, String place, String description) {
        this.discipline = discipline;
        this.name = name;
        this.date = date;
        this.place = place;
        this.description = description;
    }
}
