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




    public Handler() {
        affiliates = new ArrayList<Affiliate>();
        teams = new ArrayList<>();
        cmps = new ArrayList<>();
    }

    public void setCmps(List<Competition> cmps) {
        this.cmps = cmps;
    }

    public void setAffiliates(List<Affiliate> affiliates) {
        this.affiliates = affiliates;
    }

}
