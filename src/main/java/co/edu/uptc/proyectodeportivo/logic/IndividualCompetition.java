package co.edu.uptc.proyectodeportivo.logic;

import java.util.List;

public class IndividualCompetition extends Competition{
    private List<Affiliate> leaderboard;

    public IndividualCompetition(String name, String date, Discipline discipline, List<Affiliate> leaderboard) {
        super(name, date, discipline);
        this.leaderboard = leaderboard;
        leaderboard.forEach(u -> u.getCompetitions().add(this.getName()));
    }

    public List<Affiliate> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<Affiliate> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
