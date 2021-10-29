package ua.alexkras.hotel;

import ua.alexkras.hotel.controller.*;
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

        log("get: "+path);
        GetCommand getCommand = (GetCommand) commands.getOrDefault(path , r->"index.jsp");
        String page = getCommand.execute(request);

        request.getRequestDispatcher(page).forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getRequestURI();
        path = path.replaceAll(".*/app/" , "");

        log("post: "+path);
        PostCommand postCommand = (PostCommand) commands.getOrDefault(path , r->"index.jsp");

        String page = postCommand.execute(request);
        /*
        request.setAttribute("students" , null);
        request.getRequestDispatcher("./WEB-INF/studentlist.jsp")
                .forward(request,response);

         */
        request.getRequestDispatcher(page).forward(request,response);
    }
}
