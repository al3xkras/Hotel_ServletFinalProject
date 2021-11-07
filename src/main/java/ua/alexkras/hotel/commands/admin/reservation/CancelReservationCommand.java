package ua.alexkras.hotel.commands.admin.reservation;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.commands.admin.AdminCommand;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.impl.ApartmentServiceImpl;
import ua.alexkras.hotel.service.impl.ReservationServiceImpl;

import javax.servlet.http.HttpServletRequest;

import static ua.alexkras.hotel.commands.user.reservation.CancelReservationCommand.parseCommandAndCancelReservationAndUpdateApartmentStatus;

public class CancelReservationCommand implements Command {

    public static final String pathBasename = "cancel";

    private final ReservationServiceImpl reservationService;
    private final ApartmentServiceImpl apartmentService;

    public CancelReservationCommand(ReservationServiceImpl reservationService,
                                    ApartmentServiceImpl apartmentService){
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
                command,
                reservationService, apartmentService,
                ReservationStatus.CANCELLED_BY_ADMIN);

        return "redirect:/"+HotelServlet.pathBasename+'/'+ AdminCommand.pathBasename;
    }

}
