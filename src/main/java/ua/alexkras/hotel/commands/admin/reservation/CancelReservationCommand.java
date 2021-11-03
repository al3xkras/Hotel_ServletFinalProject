package ua.alexkras.hotel.commands.admin.reservation;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.commands.admin.AdminCommand;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;

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
        ua.alexkras.hotel.commands.user.reservation.CancelReservationCommand.CancelReservation(request, pathBasename, reservationService, apartmentService);

        return "redirect:/"+HotelServlet.pathBasename+'/'+ AdminCommand.pathBasename;
    }

}
