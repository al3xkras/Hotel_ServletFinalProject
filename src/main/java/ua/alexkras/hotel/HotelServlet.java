package ua.alexkras.hotel;

import ua.alexkras.hotel.commands.*;
import ua.alexkras.hotel.commands.admin.AdminCommand;
import ua.alexkras.hotel.commands.admin_or_user.ApartmentCommand;
import ua.alexkras.hotel.commands.user.UserCommand;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.PaymentService;
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
        ReservationService reservationService = new ReservationService();
        UserService userService = new UserService();
        ApartmentService apartmentService = new ApartmentService();
        PaymentService paymentService = new PaymentService(reservationService);

        commands.put(UserCommand.pathBasename, new UserCommand(reservationService, apartmentService,paymentService));
        commands.put(AdminCommand.pathBasename, new AdminCommand(apartmentService,reservationService));

        commands.put(RegistrationCommand.pathBasename, new RegistrationCommand(userService));

        commands.put(LoginCommand.pathBasename,new LoginCommand(userService));
        commands.put(LogoutCommand.pathBasename, new LogoutCommand());

        commands.put(ApartmentCommand.pathBasename,new ApartmentCommand(apartmentService, reservationService));

        commands.put(ExceptionCommand.pathBasename, new ExceptionCommand());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String command = Command.getCommand(request.getRequestURI() , pathBasename);
        Command getCommand = commands.getOrDefault(command , defaultCommand);

        String page = getCommand.executeGet(request);
        if (!page.startsWith("redirect:")) {
            request.getRequestDispatcher(page).forward(request , response);
        } else {
            response.sendRedirect(request.getContextPath()+page.replace("redirect:",""));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String command = Command.getCommand(request.getRequestURI() , pathBasename);

        Command postCommand =  commands.getOrDefault(command , defaultCommand);
        String page = postCommand.executePost(request);

        if (!page.isEmpty() && !page.startsWith("redirect:")) {
            request.getRequestDispatcher(page).forward(request , response);
        } else if (!page.isEmpty()){
            response.sendRedirect(request.getContextPath()+page.replace("redirect:",""));
        }
    }
}
