package co.edu.uptc.proyectodeportivo.logic;

import java.util.List;

public class IndividualCompetition extends Competition{
    private List<User> leaderboard;

    public IndividualCompetition(String name, String date, Discipline discipline, List<User> leaderboard) {
        super(name, date, discipline);
        this.leaderboard = leaderboard;
        leaderboard.forEach(u -> u.getCompetitions().add(this.getName()));
    }

    public List<User> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<User> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
