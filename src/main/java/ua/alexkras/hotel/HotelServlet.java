package ua.alexkras.hotel;

import ua.alexkras.hotel.controller.Command;
import ua.alexkras.hotel.controller.ExceptionCommand;
import ua.alexkras.hotel.controller.LoginCommand;
import ua.alexkras.hotel.controller.RegistrationCommand;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HotelServlet extends HttpServlet {

    private final Map<String, Command> commands = new HashMap<>();

    public void init(){
        commands.put("login", new LoginCommand());
        commands.put("registration" , new RegistrationCommand());
        commands.put("exception" , new ExceptionCommand());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getRequestURI();
        path = path.replaceAll(".*/app/" , "");

        log(path);
        Command command = commands.getOrDefault(path ,r->"index.jsp");
        String page = command.execute(request);

        request.getRequestDispatcher(page).forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        /*
        request.setAttribute("students" , null);
        request.getRequestDispatcher("./WEB-INF/studentlist.jsp")
                .forward(request,response);

         */
    }
}
