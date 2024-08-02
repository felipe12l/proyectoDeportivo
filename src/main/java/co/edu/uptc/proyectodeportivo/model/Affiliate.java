package co.edu.uptc.proyectodeportivo.model;

import java.util.ArrayList;

public class Affiliate {
    private String id;
    private int age;
    private String name;
    private String lastName;
    private boolean gender;
    private ArrayList<Competition> competitions;
    public Affiliate() {
    }

    public Affiliate(String id, int age, String name, String lastName, boolean gender) {
        competitions=new ArrayList<>();
        this.id = id;
        this.age = age;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
    }

    public ArrayList<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(ArrayList<Competition> competitions) {
        this.competitions = competitions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Affiliate{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                '}';
    }
}
