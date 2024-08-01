package co.edu.uptc.proyectodeportivo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Handlin {
    private List<User> users;
    private List<Team> teams;

    public Handlin() {
        users = new ArrayList<User>();
        teams = new ArrayList<>();
    }

    public Team findTeam(String teamName) {
        Optional<Team> team = teams.stream().filter(t -> t.getName().equals(teamName)).findFirst();
        return team.orElse(null);
    }

    public boolean addTeam(String name, List<User> users, String discipline) {
        if (findTeam(name) == null) {
            Team t = new Team(name, users, discipline);
            teams.add(t);
            return true;
        }
        return false;
    }
    public List<User> getUsers() {
        return users;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public User findUser(int id){
        Optional<User> user = users.stream().filter(u -> u.getId() == id).findFirst();
        return user.orElse(null);
    }
    public boolean addUser(int id, String name, String discipline) {
        if(findUser(id) == null){
            User user = new User(id, name, discipline);
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
