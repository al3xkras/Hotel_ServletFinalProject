package ua.alexkras.hotel.commands.user.reservation;

import ua.alexkras.hotel.commands.user.UserCommand;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.ReservationService;
import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.model.ReservationStatus;

import javax.servlet.http.HttpServletRequest;



public class CancelReservationCommand implements Command {

    public static final String pathBasename = "cancel";

    private final ReservationService reservationService;

    public CancelReservationCommand(ReservationService reservationService){
        this.reservationService=reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        return "redirect:/"+ HotelServlet.pathBasename+'/'+ UserCommand.pathBasename;
    }

    @Override
    public String executePost(HttpServletRequest request) {
        int reservationId = Integer.parseInt(Command.getCommand(request.getRequestURI(), pathBasename));

        reservationService.updateReservationStatusById(reservationId, ReservationStatus.CANCELLED);

        return "redirect:/"+HotelServlet.pathBasename+"/"+ UserCommand.pathBasename;
    }

}
