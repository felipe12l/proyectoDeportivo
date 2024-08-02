package co.edu.uptc.proyectodeportivo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Group {
    private int id;
    private String nombre;
    private List<Affiliate> members;
    public Group() {}

    public Group(int id, String nombre) {
        members=new ArrayList<>();
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Affiliate> getMembers() {
        return members;
    }
    public boolean addMember(Affiliate member) {
        if(members.contains(member)) {
            return false;
        }
        members.add(member);
        return true;
    }
    public boolean removeMember(String id) {
        try{
            members.stream().filter(m->id.equals(m.getId())).findFirst().ifPresent(members::remove);
            return true;
        }catch(Exception e) {
            return false;
        }
    }
    public List<Affiliate> getAllMembers() {
        return members;
    }
    public boolean updateMember(String id,Affiliate member) {
        try{
            Optional<Affiliate> optional= members.stream().filter(m-> m.getId().equals(id)).findFirst();
            if(optional.isPresent()) {
                removeMember(optional.get().getId());
                addMember(member);
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }

    }
}
