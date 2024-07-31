package co.edu.uptc.proyectodeportivo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HandlingUsers {
    private List<User> users;

    public HandlingUsers() {
        users = new ArrayList<User>();
    }

    List<User> users;

    public HandlingUser() {
        users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public User findUser(int id){
        Optional<User> user = users.stream().filter(u -> u.getId() == id).findFirst();
        return user.orElse(null);
    }
    public boolean addUser(int id, String name) {
        if(findUser(id) == null){
            User user = new User(id, name);
            users.add(user);
            return true;
        }
        return false;
    }

    public boolean updateUser(int id, String name) {
        User u = findUser(id);
        if(u != null){
            u.setName(name);
            return true;
        }
        return false;
    }

    public boolean deleteUser(int id){
        User u = findUser(id);
        if(u != null){
            users.remove(u);
            return true;
        }
        return false;
    }
}
