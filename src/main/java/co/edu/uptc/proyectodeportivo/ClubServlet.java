package co.edu.uptc.proyectodeportivo;

import java.io.*;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "clubServlet", value = "/club-servlet")
public class ClubServlet extends HttpServlet {
    private String message;
    private HandlingUsers handlingUser;
    public void init() {
        handlingUser = new HandlingUsers();
        setup();
    }

    public void setup(){
        handlingUser.addUser(1, "A");
        handlingUser.addUser(2, "B");
        handlingUser.addUser(3, "C");
        handlingUser.addUser(4, "D");
        handlingUser.addUser(5, "E");

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Gson gson = new Gson();

        response.setContentType("application/html");
        try(PrintWriter out = response.getWriter()){
            out.println(gson.toJson( handlingUser.getUsers() ));
        }
    }

}