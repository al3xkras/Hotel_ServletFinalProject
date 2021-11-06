package ua.alexkras.hotel.commands.admin.reservation;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.commands.admin.AdminCommand;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;

import static ua.alexkras.hotel.commands.user.reservation.CancelReservationCommand.parseCommandAndCancelReservationAndUpdateApartmentStatus;

public class CancelReservationCommand implements Command {

    public static final String pathBasename = "cancel";

    private final ReservationService reservationService;
    private final ApartmentService apartmentService;

    public CancelReservationCommand(ReservationService reservationService,
                                    ApartmentService apartmentService){
        this.reservationService=reservationService;
        this.apartmentService=apartmentService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        return "redirect:/"+ HotelServlet.pathBasename+'/'+ AdminCommand.pathBasename;
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(), pathBasename);

        parseCommandAndCancelReservationAndUpdateApartmentStatus(
                command, pathBasename,
                reservationService, apartmentService,
                ReservationStatus.CANCELLED_BY_ADMIN);

        return "redirect:/"+HotelServlet.pathBasename+'/'+ AdminCommand.pathBasename;
    }

}
