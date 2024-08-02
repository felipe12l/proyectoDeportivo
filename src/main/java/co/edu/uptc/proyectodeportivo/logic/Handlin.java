package co.edu.uptc.proyectodeportivo.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Handlin {
    private List<User> users;
    private List<Team> teams;

    private List<Competition> cmps;

    public Handlin() {
        users = new ArrayList<User>();
        teams = new ArrayList<>();
        cmps = new ArrayList<>();
    }

    public Team findTeam(String teamName) {
        Optional<Team> team = teams.stream().filter(t -> t.getName().equals(teamName)).findFirst();
        return team.orElse(null);
    }

    public boolean addTeam(String name, List<User> users, Discipline discipline) {
        if (findTeam(name) == null) {
            Team t = new Team(name, users, discipline);
            teams.add(t);
            return true;
        }
        return false;
    }

    public Competition findCompetition(String name){
        Optional<Competition> cmp = cmps.stream().filter(c -> c.getName().equals(name)).findFirst();
        return cmp.orElse(null);
    }
    public boolean addIndividualCompetition(String name, String date, Discipline discipline, List<User> leaderboard){
        if(findCompetition(name) == null){
            IndividualCompetition ic = new IndividualCompetition(name, date, discipline, leaderboard);
            cmps.add(ic);
            return true;
        }
        return false;
    }

    public boolean addGroupalCompetition(String name, String date, Discipline discipline, List<Team> leaderboard){
        if(findCompetition(name) == null){
            GroupalCompetition gc = new GroupalCompetition(name, date, discipline, leaderboard);
            cmps.add(gc);
            return true;
        }
        return false;
    }


    public User findUser(int id){
        Optional<User> user = users.stream().filter(u -> u.getId() == id).findFirst();
        return user.orElse(null);
    }
    public boolean addUser(int id, String name, String lastName, int age, Discipline discipline) {
        if(findUser(id) == null){
            User user = new User(id, name, lastName, age, discipline);
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

    public List<User> getUsers() {
        return users;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Competition> getCmps() {
        return cmps;
    }

    public void setCmps(List<Competition> cmps) {
        this.cmps = cmps;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
