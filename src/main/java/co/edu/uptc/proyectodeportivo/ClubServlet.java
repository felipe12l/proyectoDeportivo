package co.edu.uptc.proyectodeportivo;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import co.edu.uptc.proyectodeportivo.logic.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "clubServlet", value = "/club-servlet")
public class ClubServlet extends HttpServlet {
    private String message;
    private Handlin handlingUser;
    public void init() {
        handlingUser = new Handlin();
        setup();
    }

    public void setup(){
        Discipline tennis = new Discipline("Tennis", false);
        Discipline f1 = new Discipline("F1", false);
        Discipline football = new Discipline("Football", true);
        Discipline basketball = new Discipline("Basketball", true);


        handlingUser.addUser(1, "A", "a", 19, tennis);
        handlingUser.addUser(2, "B", "b", 20, tennis);
        handlingUser.addUser(3, "C", "c", 21, tennis);
        handlingUser.addUser(4, "D", "d", 22, tennis);

        handlingUser.addUser(5, "E", "e", 23, football);
        handlingUser.addUser(6, "F", "f", 24, football);
        handlingUser.addUser(7, "G", "g", 25, football);
        handlingUser.addUser(8, "H", "h", 26, football);
        handlingUser.addUser(9, "I", "i", 27, football);
        handlingUser.addUser(10, "J", "j", 28, football);
        handlingUser.addUser(11, "K", "k", 29, football);
        handlingUser.addUser(12, "L", "l", 30, football);
        handlingUser.addUser(13, "M", "m", 31, football);
        handlingUser.addUser(14, "N", "n", 32, football);

        handlingUser.addUser(15, "O", "o", 33, f1);
        handlingUser.addUser(16, "P", "p", 34, f1);
        handlingUser.addUser(17, "Q", "q", 35, f1);
        handlingUser.addUser(18, "R", "r", 36, f1);
        handlingUser.addUser(19, "S", "s", 37, f1);
        handlingUser.addUser(20, "T", "t", 38, f1);
        handlingUser.addUser(21, "U", "u", 39, f1);

        handlingUser.addUser(22, "V", "v", 40, basketball);
        handlingUser.addUser(23, "W", "w", 41, basketball);
        handlingUser.addUser(24, "X", "x", 42, basketball);
        handlingUser.addUser(25, "Y", "y", 43, basketball);
        handlingUser.addUser(26, "Z", "z", 44, basketball);
        handlingUser.addUser(27, "AA", "aa", 45, basketball);
        handlingUser.addUser(28, "BB", "bb", 46, basketball);
        handlingUser.addUser(29, "CC", "cc", 47, basketball);
        handlingUser.addUser(30, "DD", "dd", 48, basketball);
        handlingUser.addUser(31, "EE", "ee", 49, basketball);

        List<User> foot1 = handlingUser.getUsers().stream().filter(u -> u.getDiscipline().getName().equals("Football")).limit(5).collect(Collectors.toList());
        List<User> foot2 = handlingUser.getUsers().stream().filter(u -> u.getDiscipline().getName().equals("Football")).skip(5).limit(5).collect(Collectors.toList());

        List<User> basket1 = handlingUser.getUsers().stream().filter(u -> u.getDiscipline().getName().equals("Basketball")).limit(5).collect(Collectors.toList());
        List<User> basket2 = handlingUser.getUsers().stream().filter(u -> u.getDiscipline().getName().equals("Basketball")).skip(5).limit(5).collect(Collectors.toList());
        handlingUser.addTeam("Football1", foot1, football);
        handlingUser.addTeam("Football2", foot2,football);
        handlingUser.addTeam("Basketball1", basket1, basketball);
        handlingUser.addTeam("Basketball2", basket2,basketball);

        handlingUser.addIndividualCompetition("Tennis 1", "2021-05-01", tennis, handlingUser.getUsers().stream().limit(4).collect(Collectors.toList()));
        handlingUser.addIndividualCompetition("F1 1", "2021-05-01", f1, handlingUser.getUsers().stream().skip(14).limit(7).collect(Collectors.toList()));


        handlingUser.addGroupalCompetition("Football 1", "2021-05-01", football, List.of(handlingUser.findTeam("Football1"),
                                                                                          handlingUser.findTeam("Football2")));

        handlingUser.addGroupalCompetition("Basketball 1", "2021-05-01", basketball, List.of(handlingUser.findTeam("Basketball1"),
                handlingUser.findTeam("Basketball2")));



    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String param = request.getParameter("param");

        response.setContentType("application/json");
        Gson gson = new Gson();

        try (PrintWriter out = response.getWriter()) {
            String discipline = request.getParameter("discipline");
            if(discipline != null){
                if ("1".equals(param)) {

                    List<User> users = handlingUser.getUsers().stream()
                            .filter(u -> u.getDiscipline().getName().equals(discipline))
                            .collect(Collectors.toList());
                    String jsonResponse = gson.toJson(users);
                    out.println(jsonResponse);
                } else if ("2".equals(param)) {

                    List<Team> groups = handlingUser.getTeams().stream()
                            .filter(t -> t.getDiscipline().getName().equals(discipline))
                            .collect(Collectors.toList());
                    String jsonResponse = gson.toJson(groups);
                    out.println(jsonResponse);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println("{\"error\":\"Invalid parameter\"}");
                }

                //If discipline parameter does not exist, then get all the users
            }else{
                if("1".equals(param)){
                    try{
                        String jsonResponse = gson.toJson(handlingUser.getUsers());
                        out.println(jsonResponse);
                    }catch(Exception e){
                        System.out.println("ERROR SENDING JSONRESPONSE (USERS)");
                    }
                }else if("2".equals(param)){
                    try{
                        String jsonResponse = gson.toJson(handlingUser.getCmps());
                        out.println(jsonResponse);
                    }catch(Exception e){
                        System.out.println("ERROR SENDING JSONRESPONSE (COMPETITIONS)");
                    }
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

            Type leaderboardType = new TypeToken<List<User>>(){}.getType();
            List<User> leaderboard = gson.fromJson(leaderboardJson, leaderboardType);


            handlingUser.addIndividualCompetition(name, date, new Discipline(discipline, false), leaderboard);

            try (PrintWriter out = response.getWriter()) {
                out.println(gson.toJson("Competition added successfully"));
            }
        }else if("2".equals(param)){


            String name = request.getParameter("name");
            String date = request.getParameter("date");
            String discipline = request.getParameter("discipline");
            String leaderboardJson = request.getParameter("leaderboard");

            Type leaderboardType = new TypeToken<List<Team>>(){}.getType();
            List<Team> leaderboard = gson.fromJson(leaderboardJson, leaderboardType);


            handlingUser.addGroupalCompetition(name, date, new Discipline(discipline, true), leaderboard);

            try (PrintWriter out = response.getWriter()) {
                out.println(gson.toJson("Competition added successfully"));
            }
        }


    }


}