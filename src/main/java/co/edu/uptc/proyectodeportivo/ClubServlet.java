package co.edu.uptc.proyectodeportivo;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
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
        handlingUser.addUser(1, "A", "Tennis");
        handlingUser.addUser(2, "B","Tennis");
        handlingUser.addUser(3, "C", "Tennis");
        handlingUser.addUser(4, "D","Tennis");
        handlingUser.addUser(5, "E", "Football");
        handlingUser.addUser(6, "F", "Football");
        handlingUser.addUser(7, "G", "Football");
        handlingUser.addUser(8, "H", "Football");
        handlingUser.addUser(9, "I", "Football");
        handlingUser.addUser(10, "J", "Football");
        handlingUser.addUser(11, "K", "Football");
        handlingUser.addUser(12, "L", "Football");
        handlingUser.addUser(13, "M", "Football");
        handlingUser.addUser(14, "N", "Football");
        handlingUser.addUser(15, "O", "F1");
        handlingUser.addUser(16, "P", "F1");
        handlingUser.addUser(17, "Q", "F1");
        handlingUser.addUser(18, "R", "F1");
        handlingUser.addUser(19, "S", "F1");
        handlingUser.addUser(20, "T", "F1");
        handlingUser.addUser(21, "U", "F1");
        handlingUser.addUser(22, "V", "Basketball");
        handlingUser.addUser(23, "W", "Basketball");
        handlingUser.addUser(24, "X", "Basketball");
        handlingUser.addUser(25, "Y", "Basketball");
        handlingUser.addUser(26, "Z", "Basketball");
        handlingUser.addUser(27, "AA", "Basketball");
        handlingUser.addUser(28, "BB", "Basketball");
        handlingUser.addUser(29, "CC", "Basketball");
        handlingUser.addUser(30, "DD", "Basketball");
        handlingUser.addUser(31, "EE", "Basketball");

        List<User> foot1 = handlingUser.getUsers().stream().filter(u -> u.getDiscipline().equals("Football")).limit(5).collect(Collectors.toList());
        List<User> foot2 = handlingUser.getUsers().stream().filter(u -> u.getDiscipline().equals("Football")).skip(5).limit(5).collect(Collectors.toList());

        List<User> basket1 = handlingUser.getUsers().stream().filter(u -> u.getDiscipline().equals("Basketball")).limit(5).collect(Collectors.toList());
        List<User> basket2 = handlingUser.getUsers().stream().filter(u -> u.getDiscipline().equals("Basketball")).skip(5).limit(5).collect(Collectors.toList());
        handlingUser.addTeam("Football1", foot1, "Football");
        handlingUser.addTeam("Football2", foot2,"Football");
        handlingUser.addTeam("Basketball1", basket1, "Basketball");
        handlingUser.addTeam("Basketball2", basket2,"Basketball");


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
                            .filter(u -> u.getDiscipline().equals(discipline))
                            .collect(Collectors.toList());
                    String jsonResponse = gson.toJson(users);
                    out.println(jsonResponse);
                } else if ("2".equals(param)) {

                    List<Team> groups = handlingUser.getTeams().stream()
                            .filter(t -> t.getDiscipline().equals(discipline))
                            .collect(Collectors.toList());
                    String jsonResponse = gson.toJson(groups);
                    out.println(jsonResponse);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println("{\"error\":\"Invalid parameter\"}");
                }

                //If discipline parameter does not exist, then get all the users
            }else{
                String jsonResponse = gson.toJson(handlingUser.getUsers());
                out.println(jsonResponse);
            }

        }
    }

}