package co.edu.uptc.proyectodeportivo.control;

import co.edu.uptc.proyectodeportivo.model.Affiliate;
import co.edu.uptc.proyectodeportivo.model.Competition;
import co.edu.uptc.proyectodeportivo.model.Discipline;
import co.edu.uptc.proyectodeportivo.persistence.AffiliateDAO;
import co.edu.uptc.proyectodeportivo.persistence.CompetitionDAO;
import co.edu.uptc.proyectodeportivo.persistence.DisciplineDAO;

import java.util.Date;
import java.util.List;

public class Controller {
    List<Affiliate> affiliates;
    List<Competition> competitions;
    List<Discipline> disciplines;
    AffiliateDAO affiliateDAO;
    CompetitionDAO competitionDAO;
    DisciplineDAO disciplineDAO;

    public Controller() {
    }
    public void addAffiliate(String id, int age, String name, String lastName, boolean gender) {}
    public void addCompetition(String id, Discipline discipline, String name, Date date, String place, String description) {}
    public void addDiscipline(String id, Discipline discipline) {}
}
