package co.edu.uptc.proyectodeportivo.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Affiliate {
    @SerializedName("id")
    private String id;
    @SerializedName("age")
    private int age;
    @SerializedName("name")
    private String name;
    @SerializedName("last name")
    private String lastName;
    @SerializedName("gender")
    private boolean gender;
    private ArrayList<Competition> competitions;
    @SerializedName("competitions")
    private List<String> competitionsKey;
    public Affiliate() {
    competitionsKey = new ArrayList<>();
    }

    public Affiliate(String id, int age, String name, String lastName, boolean gender) {
        competitionsKey=new ArrayList<>();
        competitions=new ArrayList<>();
        this.id = id;
        this.age = age;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
    }

    public List<String> getCompetitionsKey() {
        return competitionsKey;
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
