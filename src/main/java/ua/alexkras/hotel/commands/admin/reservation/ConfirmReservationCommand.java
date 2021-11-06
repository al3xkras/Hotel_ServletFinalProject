package ua.alexkras.hotel.commands.admin.reservation;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.commands.admin.AdminCommand;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;

public class ConfirmReservationCommand implements Command {

    public static final String pathBasename = "confirm";

    private final ReservationService reservationService;

    public ConfirmReservationCommand(ReservationService reservationService){
        this.reservationService=reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        return "redirect:/"+ HotelServlet.pathBasename+'/'+ AdminCommand.pathBasename;
    }

    @Override
    public String executePost(HttpServletRequest request) {
        int reservationId = Integer.parseInt(Command.getCommand(request.getRequestURI(), pathBasename));

        reservationService.updateStatusById(reservationId, ReservationStatus.CONFIRMED);

        return "redirect:/app/admin";
    }
}
