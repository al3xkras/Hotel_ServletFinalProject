package ua.alexkras.hotel.commands.admin;

import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AdminCommand implements Command {

    public static final String pathBasename = "admin";
    private final ReservationService reservationService;

    private final Map<String, Command> commands = new HashMap<>();

    public AdminCommand(ApartmentService apartmentService, ReservationService reservationService){
        this.reservationService=reservationService;

        commands.put(AddApartmentCommand.pathBasename,new AddApartmentCommand(apartmentService));
        commands.put(SelectReservationCommand.pathBasename,new SelectReservationCommand(reservationService,apartmentService));
    }
    @Override
    public String executeGet(HttpServletRequest request) {
        Optional<User> currentUser = AuthFilter.getCurrentLoginUser();

        if(!currentUser.orElseThrow(IllegalStateException::new)
                .getUserType().equals(UserType.ADMIN)){
            return "redirect:/";
        }

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        if (command.isEmpty()){
            request.setAttribute("pendingReservations",reservationService.getPendingReservations(1,5));
            return "/WEB-INF/personal_area/admin.jsp";
        }

        return Optional.ofNullable(commands.get(command))
                 .orElseThrow(IllegalStateException::new)
                 .executeGet(request);
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        return command.isEmpty() ?
                "/WEB-INF/personal_area/admin.jsp" :
                Optional.ofNullable(commands.get(command))
                        .orElseThrow(IllegalStateException::new)
                        .executePost(request);
    }
}
