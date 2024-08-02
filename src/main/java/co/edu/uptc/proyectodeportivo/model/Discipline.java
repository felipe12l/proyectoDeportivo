package co.edu.uptc.proyectodeportivo.model;

import java.util.List;

public class Discipline {
    private String id;
    private String name;
    private boolean isGroup;
    private List<String> categories;
    private String description;

    public Discipline(String id, String name, boolean isGroup, List<String> categories, String description) {
        this.id = id;
        this.name = name;
        this.isGroup = isGroup;
        this.categories = categories;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Discipline() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "name='" + name + '\'' +
                ", isGroup=" + isGroup +
                ", categories=" + categories +
                ", description='" + description + '\'' +
                '}';
    }
}
