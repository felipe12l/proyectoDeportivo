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
        handlingUser.addUser(6, "F");
        handlingUser.addUser(7, "G");
        handlingUser.addUser(8, "H");
        handlingUser.addUser(9, "I");
        handlingUser.addUser(10, "J");
        handlingUser.addUser(11, "K");
        handlingUser.addUser(12, "L");
        handlingUser.addUser(13, "M");
        handlingUser.addUser(14, "N");
        handlingUser.addUser(15, "O");
        handlingUser.addUser(16, "P");
        handlingUser.addUser(17, "Q");
        handlingUser.addUser(18, "R");
        handlingUser.addUser(19, "S");
        handlingUser.addUser(20, "T");
        handlingUser.addUser(21, "U");

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String jsonResponse = gson.toJson(handlingUser.getUsers());
            System.out.println("JSON Response: " + jsonResponse);  // Imprimir respuesta en consola
            out.println(jsonResponse);
        }
    }

}