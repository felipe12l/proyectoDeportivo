package co.edu.uptc.proyectodeportivo.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.Document;

public class Handler {
    private List<Affiliate> affiliates;
    private List<Team> teams;

    private List<Competition> cmps;

    public int convertInt(Document doc, Object toparse){
        int aux = 0;
        try {
            if (toparse instanceof Double) {
                aux = ((Double) toparse).intValue();
            } else if (toparse instanceof Integer) {
                aux = (Integer) toparse;
            } else if (toparse instanceof String) {
                aux = Integer.parseInt((String) toparse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aux;
    }

    public Affiliate documentToUser(Document doc) {
        Affiliate u = new Affiliate(
                convertInt(doc, doc.get("id")),
                doc.getString("name"),
                doc.getString("lastName"),
                convertInt(doc, doc.get("age")),
                new Discipline(doc.get("discipline", Document.class).getString("name"), doc.get("discipline", Document.class).getBoolean("isGroup"))
                );
        u.setCompetitions(doc.getList("competitions", String.class));
        return u;
    }

    public Team documentToTeam(Document doc) {
        Team t = new Team(
                doc.getString("name"),
                doc.getList("participants", Document.class).stream().map(this::documentToUser).collect(Collectors.toList()),
                new Discipline(doc.get("discipline", Document.class).getString("name"), doc.get("discipline", Document.class).getBoolean("isGroup")));

        if(!teams.stream().filter(tA -> tA.getName().equals(t.getName())).findFirst().isPresent()){
            teams.add(t);
        }
        return t;
    }

    public Document userToDocument(Affiliate affiliate) {
        return new Document("id", affiliate.getId())
                .append("name", affiliate.getName())
                .append("lastName", affiliate.getLastName())
                .append("age", affiliate.getAge())
                .append("discipline", new Document("name", affiliate.getDiscipline().getName()).append("isGroup", affiliate.getDiscipline().isGroup()))
                .append("competitions", affiliate.getCompetitions());
    }

    public Competition documentToCompetition(Document doc) {
        boolean isGroup = doc.get("discipline", Document.class).getBoolean("isGroup");

        if (isGroup) {
            List<Document> teamsDocs = doc.getList("leaderboard", Document.class);
            List<Team> teams = teamsDocs.stream().map(this::documentToTeam).collect(Collectors.toList());
            return new GroupalCompetition(
                    doc.getString("name"),
                    doc.getString("date"),
                    new Discipline(doc.get("discipline", Document.class).getString("name"), isGroup),
                    teams
            );
        } else {
            List<Document> usersDocs = doc.getList("leaderboard", Document.class);
            List<Affiliate> affiliates = usersDocs.stream().map(this::documentToUser).collect(Collectors.toList());
            return new IndividualCompetition(
                    doc.getString("name"),
                    doc.getString("date"),
                    new Discipline(doc.get("discipline", Document.class).getString("name"), isGroup),
                    affiliates
            );
        }
    }

    public Document teamToDocument(Team team) {
        return new Document("name", team.getName())
                .append("discipline", new Document("name", team.getDiscipline().getName()).append("isGroup", team.getDiscipline().isGroup()))
                .append("users", team.getParticipants().stream().map(this::userToDocument).collect(Collectors.toList()));
    }




    public Handler() {
        affiliates = new ArrayList<Affiliate>();
        teams = new ArrayList<>();
        cmps = new ArrayList<>();
    }

    public Team findTeam(String teamName) {
        Optional<Team> team = teams.stream().filter(t -> t.getName().equals(teamName)).findFirst();
        return team.orElse(null);
    }

    public boolean addTeam(String name, List<Affiliate> affiliates, Discipline discipline) {
        if (findTeam(name) == null) {
            Team t = new Team(name, affiliates, discipline);
            teams.add(t);
            return true;
        }
        return false;
    }

    public Competition findCompetition(String name){
        Optional<Competition> cmp = cmps.stream().filter(c -> c.getName().equals(name)).findFirst();
        return cmp.orElse(null);
    }
    public boolean addIndividualCompetition(String name, String date, Discipline discipline, List<Affiliate> leaderboard){
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


    public Affiliate findUser(int id){
        Optional<Affiliate> user = affiliates.stream().filter(u -> u.getId() == id).findFirst();
        return user.orElse(null);
    }
    public boolean addUser(int id, String name, String lastName, int age, Discipline discipline) {
        if(findUser(id) == null){
            Affiliate affiliate = new Affiliate(id, name, lastName, age, discipline);
            affiliates.add(affiliate);
            return true;
        }
        return false;
    }

    public boolean updateUser(int id, String name) {
        Affiliate u = findUser(id);
        if(u != null){
            u.setName(name);
            return true;
        }
        return false;
    }

    public boolean deleteUser(int id){
        Affiliate u = findUser(id);
        if(u != null){
            affiliates.remove(u);
            return true;
        }
        return false;
    }

    public List<Affiliate> getUsers() {
        return affiliates;
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

    public void setUsers(List<Affiliate> affiliates) {
        this.affiliates = affiliates;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
