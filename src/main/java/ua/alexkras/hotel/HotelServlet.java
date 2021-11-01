package ua.alexkras.hotel;

import ua.alexkras.hotel.commands.*;
import ua.alexkras.hotel.commands.admin.AdminCommand;
import ua.alexkras.hotel.commands.user.UserCommand;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.ReservationService;
import ua.alexkras.hotel.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HotelServlet extends HttpServlet {

    public static final String pathBasename = "app";

    private final Command defaultCommand = new Command() {
        @Override
        public String executeGet(HttpServletRequest request) {
            return "redirect:/exception";
        }

        @Override
        public String executePost(HttpServletRequest request) {
            return "redirect:/exception";
        }
    };

    private final Map<String, Command> commands = new HashMap<>();

    public void init(){
        commands.put("login", new LoginCommand(new UserService()));
        commands.put("registration" , new RegistrationCommand(new UserService()));
        commands.put("exception" , new ExceptionCommand());
        commands.put(UserCommand.pathBasename, new UserCommand());
        commands.put("admin", new AdminCommand(new ReservationService()));
        commands.put("logout", new LogoutCommand());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String command = Command.getCommand(request.getRequestURI(),pathBasename);
        Command getCommand = commands.getOrDefault(command , defaultCommand);

        String page = getCommand.executeGet(request);
        if (!page.startsWith("redirect:")) {
            request.getRequestDispatcher(page).forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath()+page.replace("redirect:",""));
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = request.getRequestURI();
        path = path.replaceAll(".*/app/" , "");

        Command postCommand =  commands.getOrDefault(path , defaultCommand);

        String page = postCommand.executePost(request);

        if (!page.isEmpty() && !page.startsWith("redirect:")) {
            request.getRequestDispatcher(page).forward(request, response);
        } else if (!page.isEmpty()){
            response.sendRedirect(request.getContextPath()+page.replace("redirect:",""));
        }
    }
}
