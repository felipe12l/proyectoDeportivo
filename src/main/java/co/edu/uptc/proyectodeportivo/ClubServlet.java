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

    private Handler handler;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> affiliateCollection;
    private MongoCollection<Document> teamCollection;
    private MongoCollection<Document> competitionCollection;

    public void init() {
        handler = new Handler();
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("KKKClubDeportive");
        affiliateCollection = database.getCollection("affiliates");
        teamCollection = database.getCollection("teams");
        competitionCollection = database.getCollection("competitions");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String param = request.getParameter("param");

        response.setContentType("application/json");
        Gson gson = new Gson();

        try (PrintWriter out = response.getWriter()) {
            String discipline = request.getParameter("discipline");
            if(discipline != null){
                if ("1".equals(param)) {
                    List<Document> userDocs = affiliateCollection.find(new Document("discipline.name", discipline)).into(new ArrayList<>());
                    List<Affiliate> affiliates = userDocs.stream().map(handler::documentToUser).collect(Collectors.toList());
                    handler.setAffiliates(affiliates);
                    String jsonResponse = gson.toJson(affiliates);
                    out.println(jsonResponse);
                } else if ("2".equals(param)) {
                    List<Document> teamDocs = teamCollection.find(new Document("discipline.name", discipline)).into(new ArrayList<>());
                    List<Team> teams = teamDocs.stream().map(handler::documentToTeam).collect(Collectors.toList());
                    String jsonResponse = gson.toJson(teams);
                    out.println(jsonResponse);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println("{\"error\":\"Invalid parameter\"}");
                }
            } else {
                if("1".equals(param)){
                    List<Document> userDocs = affiliateCollection.find().into(new ArrayList<>());
                    List<Affiliate> affiliates = userDocs.stream().map(handler::documentToUser).collect(Collectors.toList());
                    handler.setAffiliates(affiliates);
                    String jsonResponse = gson.toJson(affiliates);
                    out.println(jsonResponse);
                } else if("2".equals(param)){
                    List<Document> compDocs = competitionCollection.find().into(new ArrayList<>());
                    List<Competition> competitions = compDocs.stream().map(handler::documentToCompetition).collect(Collectors.toList());
                    handler.setCmps(competitions);
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

        if ("1".equals(param)) {
            String name = request.getParameter("name");
            String date = request.getParameter("date");
            String discipline = request.getParameter("discipline");
            String leaderboardJson = request.getParameter("leaderboard");

            Type leaderboardType = new TypeToken<List<Document>>(){}.getType();
            List<Document> leaderboardDocs = gson.fromJson(leaderboardJson, leaderboardType);

            List<Integer> userIds = leaderboardDocs.stream()
                    .map(doc -> doc.getDouble("id").intValue())
                    .collect(Collectors.toList());

            List<Document> updatedUserDocs = affiliateCollection.find(new Document("id", new Document("$in", userIds))).into(new ArrayList<>());

            for (Document userDoc : updatedUserDocs) {
                Integer userId = userDoc.getInteger("id");

                List<String> competitions = userDoc.getList("competitions", String.class);
                if (competitions == null) {
                    competitions = new ArrayList<>();
                }
                competitions.add(name);

                affiliateCollection.updateOne(
                        new Document("id", userId),
                        new Document("$set", new Document("competitions", competitions))
                );
            }

            List<Document> newupdatedUserDocs = affiliateCollection.find(new Document("id", new Document("$in", userIds))).into(new ArrayList<>());
            List<Document> updatedLeaderboardDocs = leaderboardDocs.stream()
                    .map(doc -> {
                        Integer userId = doc.getDouble("id").intValue();
                        return newupdatedUserDocs.stream()
                                .filter(userDoc -> userDoc.getInteger("id").equals(userId))
                                .findFirst()
                                .orElse(null);
                    })
                    .filter(doc -> doc != null)
                    .collect(Collectors.toList());


            Document competitionDoc = new Document("name", name)
                    .append("date", date)
                    .append("discipline", new Document("name", discipline).append("isGroup", false))
                    .append("leaderboard", updatedLeaderboardDocs);
            competitionCollection.insertOne(competitionDoc);

            try (PrintWriter out = response.getWriter()) {
                out.println(gson.toJson(competitionDoc));
            }
        }

        else if("2".equals(param)){
            String name = request.getParameter("name");
            String date = request.getParameter("date");
            String discipline = request.getParameter("discipline");
            String leaderboardJson = request.getParameter("leaderboard");

            Type leaderboardType = new TypeToken<List<Document>>(){}.getType();
            List<Document> leaderboardDocs = gson.fromJson(leaderboardJson, leaderboardType);

            List<String> teamNames = leaderboardDocs.stream()
                    .map(doc -> doc.getString("name"))
                    .collect(Collectors.toList());

            List<Document> completeTeamDocs = teamCollection.find(new Document("name", new Document("$in", teamNames))).into(new ArrayList<>());

            for (Document teamDoc : completeTeamDocs) {
                List<String> competitions = teamDoc.getList("competitions", String.class);
                if (competitions == null) {
                    competitions = new ArrayList<>();
                }
                competitions.add(name);
                teamCollection.updateOne(
                        new Document("name", teamDoc.getString("name")),
                        new Document("$set", new Document("competitions", competitions))
                );
            }

            List<Document> updatedTeamDocs = teamCollection.find(new Document("name", new Document("$in", teamNames))).into(new ArrayList<>());
            List<Document> detailedLeaderboardDocs = leaderboardDocs.stream()
                    .map(doc -> {
                        String teamName = doc.getString("name");
                        return updatedTeamDocs.stream()
                                .filter(teamDoc -> teamDoc.getString("name").equals(teamName))
                                .findFirst()
                                .orElse(null);
                    })
                    .filter(doc -> doc != null)
                    .collect(Collectors.toList());

            List<Document> allUsers = affiliateCollection.find().into(new ArrayList<>());
            for (Document teamDoc : detailedLeaderboardDocs) {
                List<Document> participants = teamDoc.getList("participants", Document.class);
                for (Document participant : participants) {
                    int userId = participant.getInteger("id");

                    Document userDoc = allUsers.stream()
                            .filter(doc -> doc.getInteger("id") == userId)
                            .findFirst()
                            .orElse(null);

                    if (userDoc != null) {
                        List<String> competitions = userDoc.getList("competitions", String.class);
                        if (competitions == null) {
                            competitions = new ArrayList<>();
                        }
                        competitions.add(name);
                        affiliateCollection.updateOne(
                                new Document("id", userId),
                                new Document("$set", new Document("competitions", competitions))
                        );
                    }
                }
            }

            Document competitionDoc = new Document("name", name)
                    .append("date", date)
                    .append("discipline", new Document("name", discipline).append("isGroup", true))
                    .append("leaderboard", detailedLeaderboardDocs);

            competitionCollection.insertOne(competitionDoc);

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
