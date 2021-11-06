package ua.alexkras.hotel.commands.user;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.commands.user.reservation.CancelReservationCommand;
import ua.alexkras.hotel.commands.user.reservation.ConfirmReservationCommand;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.exception.CommandNotFoundException;
import ua.alexkras.hotel.exception.UserNotFoundException;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReservationCommand implements Command {

    public static final String pathBasename = "reservation";

    private final ReservationService reservationService;

    private final Map<String,Command> commands = new HashMap<>();

    public ReservationCommand(ReservationService reservationService, ApartmentService apartmentService){
        commands.put(ConfirmReservationCommand.pathBasename,new ConfirmReservationCommand(reservationService));
        commands.put(CancelReservationCommand.pathBasename, new CancelReservationCommand(reservationService,apartmentService));

        this.reservationService=reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        User user = AuthFilter.getCurrentLoginUser().orElseThrow(UserNotFoundException::new);

        if (!commands.containsKey(command)){
            int reservationId = Integer.parseInt(Command.getCommand(request.getRequestURI(), pathBasename));

            Reservation reservation = reservationService.getReservationById(reservationId)
                    .orElseThrow(()->new RuntimeException("Reservation not found"));

            if (!reservation.getUserId().equals(user.getId()) ||
                    !reservation.isCompleted()){
                return "redirect:/"+ HotelServlet.pathBasename +"/"+UserCommand.pathBasename;
            }

            request.setAttribute("reservation", reservation);
            return "/WEB-INF/reservation/reservation.jsp";
        }

        return Optional.ofNullable(commands.get(command))
                .orElseThrow(CommandNotFoundException::new)
                .executeGet(request);
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        return command.isEmpty() ?
                "redirect:/app/"+HotelServlet.pathBasename+'/'+UserCommand.pathBasename :
                Optional.ofNullable(commands.get(command))
                        .orElseThrow(CommandNotFoundException::new)
                        .executePost(request);
    }
}
