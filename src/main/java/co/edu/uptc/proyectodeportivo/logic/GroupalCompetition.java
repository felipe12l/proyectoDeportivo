package co.edu.uptc.proyectodeportivo.logic;

import java.util.List;

public class GroupalCompetition extends Competition {
    private List<Team> leaderboard;

    public GroupalCompetition(String name, String date, Discipline discipline, List<Team> leaderboard) {
        super(name, date, discipline);
        this.leaderboard = leaderboard;
        leaderboard.forEach(t -> t.getParticipants().forEach(u -> u.getCompetitions().add(this.getName())));
    }

    public List<Team> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<Team> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
