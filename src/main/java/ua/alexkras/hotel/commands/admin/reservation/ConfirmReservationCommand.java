package ua.alexkras.hotel.commands.admin.reservation;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.commands.admin.AdminCommand;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.impl.ReservationServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public class ConfirmReservationCommand implements Command {

    public static final String pathBasename = "confirm";

    private final ReservationServiceImpl reservationService;

    public ConfirmReservationCommand(ReservationServiceImpl reservationService){
        this.reservationService=reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        return "redirect:/"+ HotelServlet.pathBasename+'/'+ AdminCommand.pathBasename;
    }

    @Override
    public String executePost(HttpServletRequest request) {
        int reservationId = Integer.parseInt(Command.getCommand(request.getRequestURI(), pathBasename));

        reservationService.updateStatusAndConfirmationDateById(
                reservationId, ReservationStatus.CONFIRMED, LocalDate.now());

        return "redirect:/app/admin";
    }
}
