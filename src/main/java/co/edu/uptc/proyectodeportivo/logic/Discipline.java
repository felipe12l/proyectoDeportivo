package co.edu.uptc.proyectodeportivo.logic;

public class Discipline {
    private String name;
    private boolean isGroup;

    public Discipline(String name, boolean isGroup) {
        this.name = name;
        this.isGroup = isGroup;
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
}
