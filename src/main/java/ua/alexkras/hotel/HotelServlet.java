package ua.alexkras.hotel;

import ua.alexkras.hotel.commands.*;
import ua.alexkras.hotel.model.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HotelServlet extends HttpServlet {

    private final Command defaultCommand = new Command() {
        @Override
        public String executeGet(HttpServletRequest request) {
            return "index.jsp";
        }

        @Override
        public String executePost(HttpServletRequest request) {
            return "/";
        }
    };

    private final Map<String, Command> commands = new HashMap<>();

    public void init(){
        commands.put("login", new LoginCommand());
        commands.put("registration" , new RegistrationCommand());
        commands.put("exception" , new ExceptionCommand());
        commands.put("personal_area", new PersonalAreaCommand());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getRequestURI();
        path = path.replaceAll(".*/app/" , "");


        Command getCommand = commands.getOrDefault(path , defaultCommand);

        String page = getCommand.executeGet(request);

        request.getRequestDispatcher(page).forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = request.getRequestURI();
        path = path.replaceAll(".*/app/" , "");

        Command postCommand =  commands.getOrDefault(path , defaultCommand);

        String page = postCommand.executePost(request);

        response.sendRedirect(request.getContextPath() + page);
    }
}
