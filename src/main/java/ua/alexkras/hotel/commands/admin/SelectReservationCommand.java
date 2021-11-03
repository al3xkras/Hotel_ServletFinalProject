package ua.alexkras.hotel.commands.admin;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.commands.admin.reservation.CancelReservationCommand;
import ua.alexkras.hotel.commands.admin.reservation.ConfirmReservationCommand;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SelectReservationCommand implements Command {

    public static final String pathBasename = "reservation";

    private final ReservationService reservationService;

    private final Map<String,Command> commands = new HashMap<>();

    public SelectReservationCommand(ReservationService reservationService, ApartmentService apartmentService){
        commands.put(ConfirmReservationCommand.pathBasename,new ConfirmReservationCommand(reservationService));
        commands.put(CancelReservationCommand.pathBasename, new CancelReservationCommand(reservationService,apartmentService));

        this.reservationService=reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        if (!commands.containsKey(command)){
            int reservationId = Integer.parseInt(Command.getCommand(request.getRequestURI(), pathBasename));

            Reservation reservation = reservationService.getReservationById(reservationId).orElseThrow(IllegalStateException::new);
            request.setAttribute("reservation", reservation);
            return "/WEB-INF/reservation/reservation.jsp";
        }

        return Optional.ofNullable(commands.get(command))
                .orElseThrow(IllegalStateException::new)
                .executeGet(request);
    }

    @Override
    public String executePost(HttpServletRequest request) {

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        return command.isEmpty() ?
                "redirect:/"+ HotelServlet.pathBasename+"/"+ AdminCommand.pathBasename :
                Optional.ofNullable(commands.get(command))
                        .orElseThrow(IllegalStateException::new)
                        .executePost(request);
    }
}
