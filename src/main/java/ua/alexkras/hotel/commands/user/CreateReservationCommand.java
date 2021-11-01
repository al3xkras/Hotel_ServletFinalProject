package ua.alexkras.hotel.commands.user;

import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;

public class CreateReservationCommand implements Command {

    public static final String pathBasename = "create_reservation";

    private final ReservationService reservationService;

    public CreateReservationCommand(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        return "/WEB-INF/reservation/create_reservation.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        return "redirect:/"+UserCommand.pathBasename;
    }
}
