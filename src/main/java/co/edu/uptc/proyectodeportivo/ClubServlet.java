package co.edu.uptc.proyectodeportivo;

import co.edu.uptc.proyectodeportivo.logic.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "clubServlet", value = "/club-servlet")
public class ClubServlet extends HttpServlet {

    private Handlin handlin;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> userCollection;
    private MongoCollection<Document> teamCollection;
    private MongoCollection<Document> competitionCollection;

    public void init() {
        handlin = new Handlin();
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("KKKDeportiveClub");
        userCollection = database.getCollection("affiliates");
        teamCollection = database.getCollection("teams");
        competitionCollection = database.getCollection("competitions");
        setup();
    }

    public void setup() {
        // Insert initial data into MongoDB here
        // You'll need to convert your objects to MongoDB Documents
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String param = request.getParameter("param");

        response.setContentType("application/json");
        Gson gson = new Gson();

        try (PrintWriter out = response.getWriter()) {
            String discipline = request.getParameter("discipline");
            if(discipline != null){
                if ("1".equals(param)) {
                    List<Document> userDocs = userCollection.find(new Document("discipline.name", discipline)).into(new ArrayList<>());
                    List<User> users = userDocs.stream().map(handlin::documentToUser).collect(Collectors.toList());
                    handlin.setUsers(users);
                    String jsonResponse = gson.toJson(users);
                    out.println(jsonResponse);
                } else if ("2".equals(param)) {
                    List<Document> teamDocs = teamCollection.find(new Document("discipline.name", discipline)).into(new ArrayList<>());
                    List<Team> teams = teamDocs.stream().map(handlin::documentToTeam).collect(Collectors.toList());
                    String jsonResponse = gson.toJson(teams);
                    out.println(jsonResponse);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println("{\"error\":\"Invalid parameter\"}");
                }
            } else {
                if("1".equals(param)){
                    List<Document> userDocs = userCollection.find().into(new ArrayList<>());
                    List<User> users = userDocs.stream().map(handlin::documentToUser).collect(Collectors.toList());
                    handlin.setUsers(users);
                    String jsonResponse = gson.toJson(users);
                    out.println(jsonResponse);
                } else if("2".equals(param)){
                    List<Document> compDocs = competitionCollection.find().into(new ArrayList<>());
                    List<Competition> competitions = compDocs.stream().map(handlin::documentToCompetition).collect(Collectors.toList());
                    handlin.setCmps(competitions);
                    String jsonResponse = gson.toJson(compDocs);
                    out.println(jsonResponse);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        String param = request.getParameter("param");

        if("1".equals(param)){
            String name = request.getParameter("name");
            String date = request.getParameter("date");
            String discipline = request.getParameter("discipline");
            String leaderboardJson = request.getParameter("leaderboard");

            Type leaderboardType = new TypeToken<List<Document>>(){}.getType();
            List<Document> leaderboardDocs = gson.fromJson(leaderboardJson, leaderboardType);
            List<User> leaderboard = leaderboardDocs.stream().map(handlin::documentToUser).collect(Collectors.toList());

            Document competitionDoc = new Document("name", name)
                    .append("date", date)
                    .append("discipline", new Document("name", discipline).append("isGroup", false))
                    .append("leaderboard", leaderboardDocs);
            competitionCollection.insertOne(competitionDoc);

            handlin.addIndividualCompetition(name, date, new Discipline(discipline, false), leaderboard);

            try (PrintWriter out = response.getWriter()) {
                out.println(gson.toJson(competitionDoc));
            }
        } else if("2".equals(param)){
            String name = request.getParameter("name");
            String date = request.getParameter("date");
            String discipline = request.getParameter("discipline");
            String leaderboardJson = request.getParameter("leaderboard");

            Type leaderboardType = new TypeToken<List<Document>>(){}.getType();
            List<Document> leaderboardDocs = gson.fromJson(leaderboardJson, leaderboardType);
            List<Team> leaderboard = leaderboardDocs.stream().map(handlin::documentToTeam).collect(Collectors.toList());

            Document competitionDoc = new Document("name", name)
                    .append("date", date)
                    .append("discipline", new Document("name", discipline).append("isGroup", true))
                    .append("leaderboard", leaderboardDocs);
            competitionCollection.insertOne(competitionDoc);

            handlin.addGroupalCompetition(name, date, new Discipline(discipline, true), leaderboard);

            try (PrintWriter out = response.getWriter()) {
                out.println(gson.toJson(competitionDoc));
            }
        }
    }

    @Override
    public void destroy() {
        mongoClient.close();
    }
}
