package co.edu.uptc.proyectodeportivo.logic;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String name;
    private String lastName;
    private int age;
    private Discipline discipline;
    private List<String> competitions;

    public User(int id, String name, String lastName, int age, Discipline discipline) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.discipline = discipline;
        competitions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public List<String> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<String> competitions) {
        this.competitions = competitions;
    }
}
